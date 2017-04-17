package acropollis.municipali.omega.user.async.statistics;

import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import acropollis.municipali.omega.user.cache.statistics.UserStatisticsCache;
import acropollis.municipali.omega.user.data.dto.article.Article;
import acropollis.municipali.omega.user.data.dto.article.question.Question;
import acropollis.municipali.omega.user.data.dto.article.question.answer.Answer;
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
        Map<Long, Map<Long, Map<Long, Long>>> statisitcs = new HashMap<>();

        for (Article article : articlesCache.getArticles()) {
            statisitcs.put(article.getId(), new HashMap<>());

            for (Question question : article.getQuestions()) {
                statisitcs.get(article.getId()).put(question.getId(), new HashMap<>());

                for (Answer answer : question.getAnswers()) {
                    long votesAmount = userAnswerDao.countQuestionAnswersAmount(
                            article.getId(),
                            question.getId(),
                            answer.getId()
                    );

                    statisitcs.get(article.getId()).get(question.getId()).put(answer.getId(), votesAmount);
                }
            }
        }

        userStatisticsCache.set(statisitcs);
    }
}
