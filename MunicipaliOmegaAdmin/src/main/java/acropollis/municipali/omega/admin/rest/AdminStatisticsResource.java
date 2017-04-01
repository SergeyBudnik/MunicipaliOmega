package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.GetStatisticsRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.statistics.AdminStatisticsRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<Long, Long> getStatistics(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody GetStatisticsRequest request) {

        return adminStatisticsRestService.getStatistics(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                request
        );
    }
}
