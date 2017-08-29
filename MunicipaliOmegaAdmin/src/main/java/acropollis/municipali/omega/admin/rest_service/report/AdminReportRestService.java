package acropollis.municipali.omega.admin.rest_service.report;

import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;

import java.util.List;

public interface AdminReportRestService {
    List<Report> getAllReports(MunicipaliUserInfo customerInfo);
    Report getReport(MunicipaliUserInfo customerInfo, long id);
    void deleteReport(MunicipaliUserInfo customerInfo, long id);
}
