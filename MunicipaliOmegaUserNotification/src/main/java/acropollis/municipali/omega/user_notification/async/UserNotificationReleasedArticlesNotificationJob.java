package acropollis.municipali.omega.user_notification.async;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.TranslatedArticle;
import acropollis.municipali.omega.database.db.service.push.article.ArticleReleasePushService;
import acropollis.municipali.omega.health_check.async.CommonHealthcheckedJob;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.CommonReloadJobHealth;
import acropollis.municipali.omega.user_notification.data.health_check.UserNotificationHealth;
import acropollis.municipali.omega.user_notification.utils.log.LogUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
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
public class UserNotificationReleasedArticlesNotificationJob extends CommonHealthcheckedJob<UserNotificationHealth, CommonReloadJobHealth> {
    private static final Logger log = LogUtils.getReleasedArticlesNotificationJobLogger();

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

    @Autowired
    private HealthCheckCache healthCheckCache;

    private long lastReloadDate = -1;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void reload() {
        onReloadStarted();

        long reloadStartDate = new Date().getTime();

        try {
            if (lastReloadDate != -1) {
                int totalAttempts = 0;
                int successfullAttempts = 0;

                for (Article article : articleReleasePushService.getArticlesToRelease(reloadStartDate)) {
                    totalAttempts++;

                    if (sendPush(article)) {
                        successfullAttempts++;
                    }

                    articleReleasePushService.delete(article.getId());
                }

                updateHealthWithSuccess(
                        totalAttempts,
                        successfullAttempts,
                        reloadStartDate,
                        new Date().getTime()
                );
            }

            lastReloadDate = reloadStartDate;
        } catch (Exception e) {
            updateHealthWithFailure(
                    reloadStartDate,
                    new Date().getTime(),
                    e
            );
        }
    }

    private boolean sendPush(Article article) {
        try {
            WebResource resource = Client.create().resource("https://fcm.googleapis.com/fcm/send");

            TranslatedArticle translatedArticle = article.getTranslatedArticle().get(getLanguage());

            if (translatedArticle != null) {
                String formattedTitle = translatedArticle.getTitle().substring(0, min(
                        translatedArticle.getTitle().length(), MAX_TEXT_LENGTH
                ));

                String formattedText = translatedArticle.getText().substring(0, min(
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

            return true;
        } catch (IOException e) {
            log.error("Released articles push notification sending failed", e);

            return false;
        }
    }

    @Override
    protected HealthCheckCache getHealthCheckCache() {
        return healthCheckCache;
    }

    @Override
    protected UserNotificationHealth getHealthEntity() {
        return new UserNotificationHealth();
    }

    @Override
    protected CommonReloadJobHealth getReloadJobHealthEntity() {
        return new CommonReloadJobHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserNotificationHealth userHealth, CommonReloadJobHealth reloadJobHealth) {
        userHealth.setReleasedArticlesNotificationReloadJobHealth(reloadJobHealth);
    }

    private void updateHealthWithSuccess(
            int totalAttempts,
            int successfulAttempts,
            long startDate,
            long endDate
    ) {
        onReloadSuccess(startDate, endDate);

        log.info(String.format("%d of %d push notifications were sent in %d ms",
                successfulAttempts,
                totalAttempts,
                endDate - startDate
        ));
    }

    private void updateHealthWithFailure(long startDate, long endDate, Exception failureReason) {
        onReloadFailure(startDate, endDate, failureReason);

        log.error(
                String.format("Articles failed to reload in %d ms", endDate - startDate),
                failureReason
        );
    }
}