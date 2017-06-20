package acropollis.municipali.omega.user_notification.async;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.TranslatedArticle;
import acropollis.municipali.omega.database.db.service.push.article.ArticleReleasePushService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;
import static acropollis.municipali.omega.common.config.PropertiesConfig.getLanguage;
import static java.lang.Math.*;

@Service
public class ReleasedArticlesNotificationJob {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class ReleaseArticlePushNotificationPayload {
        @NoArgsConstructor
        @AllArgsConstructor
        @lombok.Data
        private static class Data {
            private String title;
            private String body;
        }

        private String to;
        private Data notification;
    }

    private static final String SERVER_KEY = PropertiesConfig.config.getGmsKey().getValue();
    private static final String ARTICLE_RELEASE_TOPIC = "ArticleRelease";

    private static final int MAX_TEXT_LENGTH = 32;

    @Autowired
    private ArticleReleasePushService articleReleasePushService;

    private long lastReloadDate = -1;

    @Scheduled(fixedDelay = 5 * 1000)
    @Transactional
    public void reload() {
        long currentDate = new Date().getTime();

        if (lastReloadDate != -1) {
            List<Article> articles = articleReleasePushService.getArticlesToRelease(currentDate);

            articles.forEach(it -> {
                sendPush(it);

                articleReleasePushService.delete(it.getId());
            });
        }

        lastReloadDate = currentDate;
    }

    private void sendPush(Article article) {
        try {
            WebResource resource = Client.create().resource("https://fcm.googleapis.com/fcm/send");

            TranslatedArticle translatedArticle = article.getTranslatedArticle().get(getLanguage());

            if (translatedArticle != null) {
                String formattedTitle = translatedArticle.getTitle().substring(0, max(
                        translatedArticle.getTitle().length(), MAX_TEXT_LENGTH
                ));

                String formattedText = translatedArticle.getText().substring(0, max(
                        translatedArticle.getText().length(), MAX_TEXT_LENGTH
                ));

                ReleaseArticlePushNotificationPayload payload = new ReleaseArticlePushNotificationPayload(
                        "/topics/" + config.getId().getValue() + "-User_" + ARTICLE_RELEASE_TOPIC,
                        new ReleaseArticlePushNotificationPayload.Data(
                                formattedTitle,
                                formattedText
                        )
                );

                resource
                        .header("Authorization", "key=" + SERVER_KEY)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .post(ClientResponse.class, new ObjectMapper().writeValueAsString(payload));
            }
        } catch (IOException e) {
            /* Do nothing */
        }
    }
}
