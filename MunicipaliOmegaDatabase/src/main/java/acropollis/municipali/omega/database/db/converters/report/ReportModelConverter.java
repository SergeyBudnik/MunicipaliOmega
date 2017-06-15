package acropollis.municipali.omega.database.db.converters.report;

import acropollis.municipali.omega.common.dto.report.Report;
import acropollis.municipali.omega.database.db.model.report.ReportModel;

import static acropollis.municipali.omega.common.utils.common.EncodingUtils.fromBase64;

public class ReportModelConverter {
    public static Report convert(ReportModel reportModel) {
        Report report = new Report(); {
            report.setId(reportModel.getId());
            report.setUserId(reportModel.getUserId());
            report.setComment(fromBase64(reportModel.getComment()));
            report.setLatitude(reportModel.getLatitude());
            report.setLongitude(reportModel.getLongitude());
        }

        return report;
    }
}
