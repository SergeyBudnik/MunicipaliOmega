package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import acropollis.municipali.omega.health_check.async.CommonHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import acropollis.municipali.omega.user.cache.statistics.UserStatisticsCache;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
import acropollis.municipali.omega.user.utils.log.LogUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAnswersReloadJob extends CommonHealthCheck<UserHealth, CommonHealth> {
    private static final Logger log = LogUtils.getUserAnswersReloadLogger();

    @Autowired
    private UserAnswerDao userAnswerDao;
    @Autowired
    private VisibleArticlesCache articlesCache;
    @Autowired
    private UserStatisticsCache userStatisticsCache;

    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        onReloadStarted();

        long reloadStartDate = new Date().getTime();

        try {
            Map<Long, Map<Long, Map<Long, Long>>> statistics = new HashMap<>();

            for (Article article : articlesCache.getArticles()) {
                statistics.put(article.getId(), new HashMap<>());

                for (Question question : article.getQuestions()) {
                    statistics.get(article.getId()).put(question.getId(), new HashMap<>());

                    for (Answer answer : question.getAnswers()) {
                        long votesAmount = userAnswerDao.countUserAnswersAmountByArticleIdAndQuestionIdAndAnswerId(
                                article.getId(),
                                question.getId(),
                                answer.getId()
                        );

                        statistics.get(article.getId()).get(question.getId()).put(answer.getId(), votesAmount);
                    }
                }
            }

            userStatisticsCache.set(statistics);

            updateHealthWithSuccess(
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
    protected CommonHealth getReloadJobHealthEntity() {
        return new CommonHealth();
    }

    @Override
    protected UserHealth getHealthEntity() {
        return new UserHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserHealth userHealth, CommonHealth reloadJobHealth) {
        userHealth.setStatisticsReloadJobHealth(reloadJobHealth);
    }

    private void updateHealthWithSuccess(long startDate, long endDate) {
        onReloadSuccess(startDate, endDate);

        log.info(String.format("User answers reloaded in %d ms", endDate - startDate));
    }

    private void updateHealthWithFailure(long startDate, long endDate, Exception failureReason) {
        onReloadFailure(startDate, endDate, failureReason);

        log.error(String.format("User answers failed to reload in %d ms", endDate - startDate), failureReason);
    }
}
