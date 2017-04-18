package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.admin.data.converters.answer.UserAnswerModelConverter;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.answer.UserAnswer;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import acropollis.municipali.omega.database.db.dao.UserDao;
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

    @Transactional(readOnly = true)
    @Override
    public Map<Long, Long> getStatistics(CustomerInfo customerInfo, GetStatisticsRequest request) {
        Map<Long, Long> res = new HashMap<>();

        for (long answerId : request.getQuestionCriteria().getAnswersIds()) {
            long answersAmount = userAnswerDao.countStatistics(
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
    public List<UserAnswer> getFullStatistics(CustomerInfo customerInfo, GetFullStatisticsRequest request) {
        return userAnswerDao
                .findAll()
                .stream()
                .map(userAnswerModel -> {
                    Optional<UserModel> user = Optional
                            .ofNullable(
                                    userDao.findByAuthToken(userAnswerModel.getUserAuthToken())
                            );

                    return UserAnswerModelConverter.convert(
                            userAnswerModel,
                            user.orElse(new UserModel())
                    );
                })
                .collect(Collectors.toList());
    }
}
