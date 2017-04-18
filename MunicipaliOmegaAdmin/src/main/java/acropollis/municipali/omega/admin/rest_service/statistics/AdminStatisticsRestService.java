package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetStatisticsRequest;
import acropollis.municipali.omega.common.dto.answer.UserAnswer;

import java.util.List;
import java.util.Map;

public interface AdminStatisticsRestService {
    Map<Long, Long> getStatistics(CustomerInfo customerInfo, GetStatisticsRequest request);
    List<UserAnswer> getFullStatistics(CustomerInfo customerInfo, GetFullStatisticsRequest request);
}
