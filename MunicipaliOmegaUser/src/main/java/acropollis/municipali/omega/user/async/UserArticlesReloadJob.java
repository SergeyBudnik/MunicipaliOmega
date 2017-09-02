package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.health_check.async.CommonHealthcheckedJob;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
import acropollis.municipali.omega.user.utils.log.LogUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static acropollis.municipali.omega.database.db.converters.article.ArticleModelConverter.convert;

@Service
public class UserArticlesReloadJob extends CommonHealthcheckedJob<UserHealth, UserHealth.ArticlesReloadJobHealth> {
    private static final Logger log = LogUtils.getArticlesReloadLogger();

    @Autowired
    private VisibleArticlesCache visibleArticlesCache;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        onReloadStarted();

        long reloadStartDate = new Date().getTime();

        try {
            List<Article> visibleArticles = new ArrayList<>();

            List<ArticleModel> articlesModels = articleDao
                    .findByIsDeletedIsFalseAndReleaseDateLessThanAndExpirationDateGreaterThan(
                            reloadStartDate,
                            reloadStartDate
                    );

            for (ArticleModel articleModel : articlesModels) {
                visibleArticles.add(convert(articleModel));
            }

            visibleArticlesCache.setArticles(visibleArticles);

            updateHealthWithSuccess(
                    visibleArticles.size(),
                    reloadStartDate,
                    new Date().getTime()
            );
        } catch (Exception e) {
            updateHealthWithFailure(reloadStartDate, new Date().getTime(), e);
        }
    }

    @Override
    protected HealthCheckCache getHealthCheckCache() {
        return healthCheckCache;
    }

    @Override
    protected UserHealth getHealthEntity() {
        return new UserHealth();
    }

    @Override
    protected UserHealth.ArticlesReloadJobHealth getReloadJobHealthEntity() {
        return new UserHealth.ArticlesReloadJobHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserHealth userHealth, UserHealth.ArticlesReloadJobHealth reloadJobHealth) {
        userHealth.setArticlesReloadJobHealth(reloadJobHealth);
    }

    private void updateHealthWithSuccess(int amountOfArticles, long startDate, long endDate) {
        onReloadSuccess(startDate, endDate)
                .getArticlesReloadJobHealth()
                .setAmount(amountOfArticles);

        log.info(String.format("%d articles reloaded in %d ms",
                amountOfArticles,
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
