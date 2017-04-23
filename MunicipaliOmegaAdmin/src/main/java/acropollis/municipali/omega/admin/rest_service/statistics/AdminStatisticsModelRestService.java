package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;
import acropollis.municipali.omega.admin.data.request.statistics.GetCollapsedStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetQuestionStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.converters.user.UserModelConverter;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import acropollis.municipali.omega.database.db.dao.UserDao;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.database.db.service.answer.AnswerService;
import acropollis.municipali.omega.database.db.service.article.ArticleService;
import acropollis.municipali.omega.database.db.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminStatisticsModelRestService implements AdminStatisticsRestService {
    @Autowired
    private UserAnswerDao userAnswerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @Transactional(readOnly = true)
    @Override
    public Map<Long, Long> getStatistics(CustomerInfo customerInfo, GetCollapsedStatisticsRequest request) {
        Map<Long, Long> res = new HashMap<>();

        for (long answerId : request.getQuestionCriteria().getAnswersIds()) {
            long answersAmount = userAnswerDao.countUserAnswersAmountByArticleIdAndQuestionIdAndAnswerId(
                    request.getQuestionCriteria().getArticleId(),
                    request.getQuestionCriteria().getQuestionId(),
                    answerId
            );

            res.put(answerId, answersAmount);
        }

        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserAnswerStatisticsCsvRow> getFullStatisticsAsCsv(
            CustomerInfo customerInfo,
            GetFullStatisticsRequest request
    ) {
        List<UserAnswerModel> userAnswerModels = userAnswerDao.findAll();

        List<UserAnswerStatisticsCsvRow> rows = new ArrayList<>();

        for (UserAnswerModel userAnswerModel : userAnswerModels) {
            Optional<Article> article = articleService.get(userAnswerModel.getArticleId());

            if (!article.isPresent()) {
                continue;
            }

            Optional<Question> question = questionService.get(article.get(), userAnswerModel.getQuestionId());

            if (!question.isPresent()) {
                continue;
            }

            Optional<Answer> answer = answerService.get(question.get(), userAnswerModel.getAnswerId());

            if (!answer.isPresent()) {
                continue;
            }

            Optional<User> user = getUserByAuthToken(userAnswerModel.getUserAuthToken());

            UserAnswerStatisticsCsvRow userAnswerStatisticsCsvRow = new UserAnswerStatisticsCsvRow();

            userAnswerStatisticsCsvRow.setUser(user);
            userAnswerStatisticsCsvRow.setArticle(article.get());
            userAnswerStatisticsCsvRow.setQuestion(question.get());
            userAnswerStatisticsCsvRow.setAnswer(answer.get());

            rows.add(userAnswerStatisticsCsvRow);
        }

        return rows;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserAnswerStatisticsCsvRow> getQuestionStatisticsAsCsv(
            CustomerInfo customerInfo,
            GetQuestionStatisticsRequest request
    ) {
        Article article = articleService
                .get(request.getArticleId())
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        Question question = questionService
                .get(article, request.getQuestionId())
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        List<UserAnswerModel> userAnswerModels = userAnswerDao.getUserAnswersByArticleIdAndQuestionId(
                request.getArticleId(),
                request.getQuestionId()
        );

        List<UserAnswerStatisticsCsvRow> rows = new ArrayList<>();

        for (UserAnswerModel userAnswerModel : userAnswerModels) {
            Optional<User> user = getUserByAuthToken(userAnswerModel.getUserAuthToken());

            Optional<Answer> answer = answerService.get(question, userAnswerModel.getAnswerId());

            if (!answer.isPresent()) {
                continue;
            }

            UserAnswerStatisticsCsvRow userAnswerStatisticsCsvRow = new UserAnswerStatisticsCsvRow();

            userAnswerStatisticsCsvRow.setUser(user);
            userAnswerStatisticsCsvRow.setArticle(article);
            userAnswerStatisticsCsvRow.setQuestion(question);
            userAnswerStatisticsCsvRow.setAnswer(answer.get());

            rows.add(userAnswerStatisticsCsvRow);
        }

        return rows;
    }

    private Optional<User> getUserByAuthToken(String authToken) {
        return Optional
                .ofNullable(userDao.findByAuthToken(authToken))
                .map(UserModelConverter::convert);
    }
}
