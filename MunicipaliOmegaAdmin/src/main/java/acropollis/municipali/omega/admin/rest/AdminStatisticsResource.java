package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetStatisticsRequest;
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

    @RequestMapping(value = "/filtered", method = RequestMethod.POST)
    public Map<Long, Long> getStatistics(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetStatisticsRequest request) {

        return adminStatisticsRestService.getStatistics(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                request
        );
    }

    @RequestMapping(value = "/full", method = RequestMethod.POST, produces = "text/csv")
    public void getFullStatisticsAsCsv(
            HttpServletResponse response,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetFullStatisticsRequest request
    ) throws IOException {
        List<UserAnswer> userAnswers = adminStatisticsRestService.getFullStatistics(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                request
        );

        response.getWriter().print(adminCsvAnswerService.produce(userAnswers));
    }
}
