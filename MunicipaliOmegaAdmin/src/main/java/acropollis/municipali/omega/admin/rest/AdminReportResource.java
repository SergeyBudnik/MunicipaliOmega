package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.report.AdminReportRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import acropollis.municipali.omega.common.dto.report.Report;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/report")
@Api(tags = "Report", description = "PROTECTED")
public class AdminReportResource {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @Qualifier(Qualifiers.MODEL)
    @Autowired
    private AdminReportRestService adminReportRestService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Report> getAllReports(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return adminReportRestService.getAllReports(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken)
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Report getReport(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return adminReportRestService.getReport(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] getReportPhoto(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return adminReportRestService.getReportPhoto(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteReport(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        adminReportRestService.deleteReport(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }
}
