package acropollis.municipali.omega.admin.rest_service.report;

import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.report.Report;

import java.util.List;

public interface AdminReportRestService {
    List<Report> getAllReports(CustomerInfo customerInfo);
    Report getReport(CustomerInfo customerInfo, long id);
    byte [] getReportPhoto(CustomerInfo customerInfo, long id);
    void deleteReport(CustomerInfo customerInfo, long id);
}
