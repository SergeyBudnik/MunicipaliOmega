package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.admin.data.converters.answer.UserAnswerModelConverter;
import acropollis.municipali.omega.admin.data.converters.article.ArticleModelConverter;
import acropollis.municipali.omega.admin.data.converters.user.UserModelConverter;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;
import acropollis.municipali.omega.admin.data.request.statistics.GetCollapsedStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetQuestionStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.answer.UserAnswer;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.common.exceptions.EntityIllegalStateException;
import acropollis.municipali.omega.common.exceptions.EntityNotFoundException;
import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import acropollis.municipali.omega.database.db.dao.UserDao;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.database.db.model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminStatisticsModelRestService implements AdminStatisticsRestService {
    @Autowired
    private UserAnswerDao userAnswerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticleDao articleDao;

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
            Article article = getArticleById(userAnswerModel.getArticleId());
            Question question = getQuestionById(article, userAnswerModel.getQuestionId());
            Answer answer = getAnswerById(question, userAnswerModel.getAnswerId());

            Optional<User> user = getUserByAuthToken(userAnswerModel.getUserAuthToken());

            UserAnswerStatisticsCsvRow userAnswerStatisticsCsvRow = new UserAnswerStatisticsCsvRow();

            userAnswerStatisticsCsvRow.setUser(user);
            userAnswerStatisticsCsvRow.setArticle(article);
            userAnswerStatisticsCsvRow.setQuestion(question);
            userAnswerStatisticsCsvRow.setAnswer(answer);

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
        Article article = getArticleById(request.getArticleId());
        Question question = getQuestionById(article, request.getQuestionId());

        List<UserAnswerModel> userAnswerModels = userAnswerDao.getUserAnswersByArticleIdAndQuestionId(
                request.getArticleId(),
                request.getQuestionId()
        );

        List<UserAnswerStatisticsCsvRow> rows = new ArrayList<>();

        for (UserAnswerModel userAnswerModel : userAnswerModels) {
            Optional<User> user = getUserByAuthToken(userAnswerModel.getUserAuthToken());

            Answer answer = getAnswerById(question, userAnswerModel.getAnswerId());

            UserAnswerStatisticsCsvRow userAnswerStatisticsCsvRow = new UserAnswerStatisticsCsvRow();

            userAnswerStatisticsCsvRow.setUser(user);
            userAnswerStatisticsCsvRow.setArticle(article);
            userAnswerStatisticsCsvRow.setQuestion(question);
            userAnswerStatisticsCsvRow.setAnswer(answer);

            rows.add(userAnswerStatisticsCsvRow);
        }

        return rows;
    }

    private Article getArticleById(long id) {
        return Optional
                .ofNullable(articleDao.findOne(id))
                .map(ArticleModelConverter::convert)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    private Question getQuestionById(Article article, long id) {
        return article
                .getQuestions()
                .stream()
                .filter(it -> it.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    private Answer getAnswerById(Question question, long answerId) {
        return question
                .getAnswers()
                .stream()
                .filter(it -> it.getId().equals(answerId))
                .findAny()
                .orElseThrow(() -> new EntityIllegalStateException(""));
    }

    private Optional<User> getUserByAuthToken(String authToken) {
        return Optional
                .ofNullable(userDao.findByAuthToken(authToken))
                .map(UserModelConverter::convert);
    }
}
