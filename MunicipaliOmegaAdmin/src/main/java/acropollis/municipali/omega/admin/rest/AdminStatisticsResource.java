package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.statistics.GetFullStatisticsRequest;
import acropollis.municipali.omega.admin.data.request.statistics.GetStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.statistics.AdminStatisticsRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import acropollis.municipali.omega.common.dto.answer.UserAnswer;
import acropollis.municipali.omega.common.utils.csv.CsvUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

        response.getWriter().print(CsvUtils.produce(
                Arrays.asList("Name", "Gender", "Email", "Date Of Birth", "Article Id", "Question Id", "Answer Id"),
                userAnswers,
                userAnswer -> {
                    List<String> res = new ArrayList<>();

                    res.add(userAnswer.getUser().getUserDetailsInfo().getName());
                    res.add(String.valueOf(userAnswer.getUser().getUserDetailsInfo().getUserGender()));
                    res.add(userAnswer.getUser().getUserDetailsInfo().getEmail());
                    res.add(userAnswer.getUser().getUserDetailsInfo().getDateOfBirth() == null ? null : new SimpleDateFormat("dd.MM.yyyy").format(userAnswer.getUser().getUserDetailsInfo().getDateOfBirth()));
                    res.add(String.valueOf(userAnswer.getArticleId()));
                    res.add(String.valueOf(userAnswer.getQuestionId()));
                    res.add(String.valueOf(userAnswer.getAnswerId()));

                    return res;
                }
        ));
    }
}
