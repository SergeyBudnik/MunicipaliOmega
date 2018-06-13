package acropollis.municipali.omega.user_notification.async;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.TranslatedArticle;
import acropollis.municipali.omega.database.db.service.push.article.ArticleReleasePushService;
import acropollis.municipali.omega.health_check.async.CommonHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.CommonHealth;
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
import static java.lang.Math.min;

@Service
public class UserNotificationReleasedArticlesNotificationJob extends CommonHealthCheck<UserNotificationHealth, CommonHealth> {
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

    private static final List<String> SERVER_KEYS = PropertiesConfig.config.getGmsKeys();
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
                int successfulAttempts = 0;

                for (Article article : articleReleasePushService.getArticlesToRelease(reloadStartDate)) {
                    totalAttempts++;

                    if (sendPush(article)) {
                        successfulAttempts++;
                    }

                    articleReleasePushService.delete(article.getId());
                }

                updateHealthWithSuccess(
                        totalAttempts,
                        successfulAttempts,
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
        WebResource resource = Client.create().resource(
                "https://fcm.googleapis.com/fcm/send"
        );

        article.getTranslatedArticle().forEach((language, translatedArticle) -> {
            try {
                String topic = "/topics/" + config.getId() + "-User_" + ARTICLE_RELEASE_TOPIC + "_" + language;

                sendPushToClients(resource, getPayload(topic, translatedArticle));
            } catch (Exception e) {
                log.error("Released articles push notification sending failed", e);
            }
        });

        return true;
    }

    private ReleaseArticlePushNotificationPayload getPayload(
            String topic,
            TranslatedArticle translatedArticle
    ) {
        return new ReleaseArticlePushNotificationPayload(
                topic,
                new ReleaseArticlePushNotificationPayload.Data(
                        formatText(translatedArticle.getTitle(), MAX_TEXT_LENGTH),
                        formatText(translatedArticle.getDescription(), MAX_TEXT_LENGTH)
                )
        );
    }

    private void sendPushToClients(
            WebResource resource,
            ReleaseArticlePushNotificationPayload payload
    ) throws IOException {
        for (String serverKey : SERVER_KEYS) {
            resource
                    .header("Authorization", "key=" + serverKey)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .post(ClientResponse.class, new ObjectMapper().writeValueAsString(payload));
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
    protected CommonHealth getReloadJobHealthEntity() {
        return new CommonHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserNotificationHealth userHealth, CommonHealth reloadJobHealth) {
        userHealth.setReleasedArticlesNotificationReloadJobHealth(reloadJobHealth);
    }

    private String formatText(String text, int maxLength) {
        return text.substring(0, min(text.length(), maxLength));
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
