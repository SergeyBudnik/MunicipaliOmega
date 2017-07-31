package acropollis.municipali.omega.admin.rest_service.statistics;

import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;
import acropollis.municipali.omega.admin.data.request.statistics.GetCollapsedStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetQuestionStatisticsRequest;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;

import java.util.List;
import java.util.Map;

public interface AdminStatisticsRestService {
    Map<Long, Long> getStatistics(
            MunicipaliUserInfo userInfo,
            GetCollapsedStatisticsRequest request
    );

    List<UserAnswerStatisticsCsvRow> getFullStatisticsAsCsv(
            MunicipaliUserInfo userInfo,
            GetFullStatisticsRequest request
    );

    List<UserAnswerStatisticsCsvRow> getQuestionStatisticsAsCsv(
            MunicipaliUserInfo userInfo,
            GetQuestionStatisticsRequest request
    );
}
