package acropollis.municipali.omega.database.db.service.report;

import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<Report> getAll();
    Optional<Report> get(long id);
    Optional<byte []> getPhoto(long id);
    void create(Report report, byte [] reportImage, User user);
    void delete(long id) throws EntityDoesNotExist;
}
