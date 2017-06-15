package acropollis.municipali.omega.database.db.converters.report;

import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.database.db.model.report.ReportModel;

import static acropollis.municipali.omega.common.utils.common.EncodingUtils.toBase64;

public class ReportDtoConverter {
    public static ReportModel convert(Report report) {
        ReportModel reportModel = new ReportModel(); {
            reportModel.setId(report.getId());
            reportModel.setUserId(report.getUserId());
            reportModel.setComment(toBase64(report.getComment()));
            reportModel.setLatitude(report.getLatitude());
            reportModel.setLongitude(report.getLongitude());
        }

        return reportModel;
    }
}
