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

    @Transactional(readOnly = false)
    @Override
    public void postReport(Report report, byte[] reportImage) {
        Optional<User> user = userService.getByAuthToken(report.getUserId());

        if (user.isPresent()) {
            reportService.create(report, reportImage, user.get());
        }
    }
}
