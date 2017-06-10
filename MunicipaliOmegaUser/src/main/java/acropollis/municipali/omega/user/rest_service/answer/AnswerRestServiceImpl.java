package acropollis.municipali.omega.user.rest_service.answer;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.exceptions.HttpCredentialsViolationException;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.user.cache.answer.pending.UserPendingAnswersCache;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import acropollis.municipali.omega.user.cache.statistics.UserStatisticsCache;
import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AnswerRestServiceImpl implements AnswerRestService {
    @Autowired
    private VisibleArticlesCache visibleArticlesCache;

    @Autowired
    private UserPendingAnswersCache userPendingAnswersCache;
    @Autowired
    private UserStatisticsCache userStatisticsCache;

    @Override
    public Map<Long, Long> getAnswerStatistics(long articleId, long questionId) {
        Article article = visibleArticlesCache
                .getArticle(articleId)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        Question question = article
                .getQuestions()
                .stream()
                .filter(it -> it.getId() == questionId)
                .findAny()
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        if (!question.isShowResult()) {
            throw new HttpCredentialsViolationException("");
        }

        return userStatisticsCache.get(articleId, questionId);
    }

    @Override
    public void answer(UserAnswer answer) {
        userPendingAnswersCache.addAnswer(answer);
    }
}
