package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.GetStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.statistics.StatisticsRestService;
import acropollis.municipali.omega.admin.service.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/statistics")
@Api(tags = "Statistics", description = "PROTECTED")
public class StatisticsResource {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private StatisticsRestService statisticsRestService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<Long, Long> getStatistics(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetStatisticsRequest request) {

        return statisticsRestService.getStatistics(
                authenticationService.getCustomerInfoOrThrow(authToken),
                request
        );
    }
}
