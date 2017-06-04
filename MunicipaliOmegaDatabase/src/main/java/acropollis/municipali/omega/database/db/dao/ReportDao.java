package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.report.ReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReportDao extends JpaRepository<ReportModel, Long> {
}
