package acropollis.municipali.omega.user.rest_service.report;

import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.database.db.service.report.ReportService;
import acropollis.municipali.omega.database.db.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReportRestServiceImpl implements ReportRestService {
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;

    @Transactional
    @Override
    public void postReport(Report report, byte[] reportImage) {
        userService
                .getByAuthToken(report.getUserId())
                .ifPresent(user -> reportService.create(report, reportImage, user));
    }
}
