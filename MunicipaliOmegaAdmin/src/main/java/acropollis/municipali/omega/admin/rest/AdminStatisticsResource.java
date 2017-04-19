package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;
import acropollis.municipali.omega.admin.data.request.statistics.GetCollapsedStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetQuestionStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.statistics.AdminStatisticsRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import acropollis.municipali.omega.admin.service.csv.answer.AdminCsvAnswerService;
import acropollis.municipali.omega.common.dto.answer.UserAnswer;
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
public class AdminStatisticsResource {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private AdminStatisticsRestService adminStatisticsRestService;

    @Autowired
    private AdminAuthenticationService adminAuthenticationService;
    @Autowired
    private AdminCsvAnswerService adminCsvAnswerService;

    @RequestMapping(value = "/json/article/collapsed", method = RequestMethod.POST)
    public Map<Long, Long> getCollapsedStatisticsJson(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetCollapsedStatisticsRequest request) {

        return adminStatisticsRestService.getStatistics(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                request
        );
    }

    @RequestMapping(value = "/csv/full", method = RequestMethod.POST, produces = "text/csv")
    public void getFullStatisticsAsCsv(
            HttpServletResponse response,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetFullStatisticsRequest request
    ) throws IOException {
        List<UserAnswerStatisticsCsvRow> userAnswers = adminStatisticsRestService
                .getFullStatisticsAsCsv(
                        null,//adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                        request
                );

        response.getWriter().print(adminCsvAnswerService.produce(userAnswers));
    }

    @RequestMapping(value = "/csv/question", method = RequestMethod.POST, produces = "text/csv")
    public void getQuestionStatisticsAsCsv(
            HttpServletResponse response,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetQuestionStatisticsRequest request
    ) throws IOException {
        List<UserAnswerStatisticsCsvRow> rows = adminStatisticsRestService
                .getQuestionStatisticsAsCsv(
                        null,//adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                        request
                );

        response.getWriter().print(adminCsvAnswerService.produce(rows));
    }
}
