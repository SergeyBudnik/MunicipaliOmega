package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.request.GetStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.database.db.dao.UserAnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminStatisticsModelRestService implements AdminStatisticsRestService {
    @Autowired
    private UserAnswerDao userAnswerDao;

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
}
