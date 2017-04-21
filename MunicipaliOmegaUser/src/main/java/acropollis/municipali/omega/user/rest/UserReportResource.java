package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.user.rest_service.report.ReportRestService;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@Api(tags = "Report", description = " ")
public class UserReportResource {
    @Data
    public static class PostReportRequest {
        @NonNull private Report report;
        private byte [] reportImage;
    }

    @Autowired
    private ReportRestService reportRestService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void postReport(@RequestBody PostReportRequest request) {
        reportRestService.postReport(request.getReport(), request.getReportImage());
    }
}
