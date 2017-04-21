package acropollis.municipali.omega.database.db.converters.report;

import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.database.db.model.report.ReportModel;

public class ReportModelConverter {
    public static Report convert(ReportModel reportModel) {
        Report report = new Report(); {
            report.setId(reportModel.getId());
            report.setUserId(reportModel.getUserId());
            report.setComment(reportModel.getComment());
            report.setLatitude(reportModel.getLatitude());
            report.setLongitude(reportModel.getLongitude());
        }

        return report;
    }
}
