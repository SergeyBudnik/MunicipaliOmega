package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.request.GetStatisticsRequest;

import java.util.Map;

public interface StatisticsRestService {
    Map<Long, Long> getStatistics(CustomerInfo customerInfo, GetStatisticsRequest request);
}
