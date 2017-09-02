package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.report.AdminReportRestService;
import acropollis.municipali.omega.common.dto.report.Report;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/report")
@Api(tags = "Report", description = "PROTECTED")
public class AdminReportResource extends AdminResource {
    @Qualifier(Qualifiers.MODEL)
    @Autowired
    private AdminReportRestService adminReportRestService;

    @GetMapping("")
    public Collection<Report> getAllReports(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return adminReportRestService.getAllReports(
                getUserInfo(authToken)
        );
    }

    @GetMapping("/{id}")
    public Report getReport(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return adminReportRestService.getReport(
                getUserInfo(authToken),
                id
        );
    }

    @DeleteMapping("/{id}")
    public void deleteReport(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        adminReportRestService.deleteReport(
                getUserInfo(authToken),
                id
        );
    }
}
