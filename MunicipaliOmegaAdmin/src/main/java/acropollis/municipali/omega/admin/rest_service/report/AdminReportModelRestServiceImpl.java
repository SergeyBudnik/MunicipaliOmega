package acropollis.municipali.omega.admin.rest_service.report;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import acropollis.municipali.omega.database.db.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Qualifier(Qualifiers.MODEL)
@Service
public class AdminReportModelRestServiceImpl implements AdminReportRestService {
    @Autowired
    private ReportService reportService;

    @Transactional(readOnly = true)
    @Override
    public List<Report> getAllReports(CustomerInfo customerInfo) {
        return reportService.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Report getReport(CustomerInfo customerInfo, long id) {
        return reportService
                .get(id)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional(readOnly = true)
    @Override
    public byte [] getReportPhoto(CustomerInfo customerInfo, long id) {
        return reportService
                .getPhoto(id)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteReport(CustomerInfo customerInfo, long id) {
        try {
            reportService.delete(id);
        } catch (EntityDoesNotExist e) {
            throw new HttpEntityNotFoundException("");
        }
    }
}
