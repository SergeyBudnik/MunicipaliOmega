package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;
import acropollis.municipali.omega.admin.data.request.statistics.GetCollapsedStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetQuestionStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.statistics.AdminStatisticsRestService;
import acropollis.municipali.omega.admin.service.csv.answer.AdminCsvAnswerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@Api(tags = "Statistics", description = "PROTECTED")
public class AdminStatisticsResource extends AdminResource {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private AdminStatisticsRestService adminStatisticsRestService;

    @Autowired
    private AdminCsvAnswerService adminCsvAnswerService;

    @PostMapping("/json/article/collapsed")
    public Map<Long, Long> getCollapsedStatisticsJson(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetCollapsedStatisticsRequest request) {

        return adminStatisticsRestService.getStatistics(
                getUserInfo(authToken),
                request
        );
    }

    @PostMapping(value = "/csv/full", produces = "text/csv")
    public void getFullStatisticsAsCsv(
            HttpServletResponse response,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetFullStatisticsRequest request
    ) throws IOException {
        List<UserAnswerStatisticsCsvRow> userAnswers = adminStatisticsRestService
                .getFullStatisticsAsCsv(
                        getUserInfo(authToken),
                        request
                );

        response.getWriter().print(adminCsvAnswerService.produce(userAnswers));
    }

    @PostMapping(value = "/csv/question", produces = "text/csv")
    public void getQuestionStatisticsAsCsv(
            HttpServletResponse response,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetQuestionStatisticsRequest request
    ) throws IOException {
        List<UserAnswerStatisticsCsvRow> rows = adminStatisticsRestService
                .getQuestionStatisticsAsCsv(
                        getUserInfo(authToken),
                        request
                );

        response.getWriter().print(adminCsvAnswerService.produce(rows));
    }
}
