package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;
import acropollis.municipali.omega.admin.data.request.statistics.GetCollapsedStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetQuestionStatisticsRequest;

import java.util.List;
import java.util.Map;

public interface AdminStatisticsRestService {
    Map<Long, Long> getStatistics(
            CustomerInfo customerInfo,
            GetCollapsedStatisticsRequest request
    );

    List<UserAnswerStatisticsCsvRow> getFullStatisticsAsCsv(
            CustomerInfo customerInfo,
            GetFullStatisticsRequest request
    );

    List<UserAnswerStatisticsCsvRow> getQuestionStatisticsAsCsv(
            CustomerInfo customerInfo,
            GetQuestionStatisticsRequest request
    );
}
