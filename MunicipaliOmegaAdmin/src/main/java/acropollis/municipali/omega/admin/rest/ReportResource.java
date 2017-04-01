package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.common.dto.report.Report;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/report")
@Api(tags = "Report", description = "PROTECTED")
public class ReportResource {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Report> getAllReports(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return null;
    }
}
