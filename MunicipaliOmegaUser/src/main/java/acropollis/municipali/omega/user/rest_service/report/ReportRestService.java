package acropollis.municipali.omega.user.rest_service.report;

import acropollis.municipali.omega.user.data.dto.report.Report;

public interface ReportRestService {
    void postReport(Report report, byte[] reportImage);
}
