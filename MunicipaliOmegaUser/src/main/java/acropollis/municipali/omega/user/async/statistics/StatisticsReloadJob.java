package acropollis.municipali.omega.user.async.statistics;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import acropollis.municipali.omega.user.cache.statistics.UserStatisticsCache;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsReloadJob {
    @Autowired
    private UserAnswerDao userAnswerDao;
    @Autowired
    private VisibleArticlesCache articlesCache;
    @Autowired
    private UserStatisticsCache userStatisticsCache;

    @Scheduled(fixedRate = 5 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
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
    }
}
