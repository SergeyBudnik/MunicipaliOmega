package acropollis.municipali.omega.admin.rest_service.report;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import acropollis.municipali.omega.database.db.service.report.ReportService;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
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
    public List<Report> getAllReports(MunicipaliUserInfo userInfo) {
        return reportService.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Report getReport(MunicipaliUserInfo userInfo, long id) {
        return reportService
                .get(id)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional
    @Override
    public void deleteReport(MunicipaliUserInfo userInfo, long id) {
        try {
            reportService.delete(id);
        } catch (EntityDoesNotExist e) {
            throw new HttpEntityNotFoundException("");
        }
    }
}
