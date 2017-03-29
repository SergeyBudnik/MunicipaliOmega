package acropollis.municipali.omega.user.data.converter.report;

import acropollis.municipali.omega.user.data.dto.report.Report;
import acropollis.municipali.omega.database.db.model.report.ReportModel;

public class ReportDtoConverter {
    public static ReportModel convert(Report report) {
        ReportModel reportModel = new ReportModel(); {
            reportModel.setId(report.getId());
            reportModel.setUserId(report.getUserId());
            reportModel.setComment(report.getComment());
            reportModel.setLatitude(report.getLatitude());
            reportModel.setLongitude(report.getLongitude());
        }

        return reportModel;
    }
}
