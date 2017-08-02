package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.utils.storage.SquareImageAdapter;
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

import java.util.*;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;
import static acropollis.municipali.omega.common.utils.storage.EntityImageStorageUtils.getImages;
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
            List<ArticleWithIcon> visibleArticles = new ArrayList<>();

            List<ArticleModel> articlesModels = articleDao
                    .findByIsDeletedIsFalseAndReleaseDateLessThanAndExpirationDateGreaterThan(
                            reloadStartDate,
                            reloadStartDate
                    );

            for (ArticleModel articleModel : articlesModels) {
                Article article = convert(articleModel);

                ArticleWithIcon articleWithIcon = article.withIcon(
                        getArticleIcons(article),
                        Collections.emptyMap(),
                        getAnswersIcons(article)
                );

                visibleArticles.add(articleWithIcon);
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

    private Map<Integer, byte []> getArticleIcons(Article article) {
        return SquareImageAdapter.unpack(
                getImages(
                        config.getImagesArticlesIconsLocation().getValue(),
                        article.getId()
                ).orElseGet(HashMap::new)
        );
    }

    private Map<Long, Map<Long, Map<Integer, byte []>>> getAnswersIcons(Article article) {
        Map<Long, Map<Long, Map<Integer, byte []>>> answersIcons = new HashMap<>();

        for (Question question : article.getQuestions()) {
            answersIcons.put(question.getId(), new HashMap<>());

            question.getAnswers()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(answer -> {
                        Map<Integer, byte[]> answerIcons = SquareImageAdapter
                                .unpack(
                                        getImages(
                                                config.getImagesAnswersIconsLocation().getValue(), answer.getId())
                                                .orElseGet(HashMap::new)
                                );

                        answersIcons.get(question.getId()).put(answer.getId(), answerIcons);
                    });
        }

        return answersIcons;
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
