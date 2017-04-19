package acropollis.municipali.omega.admin.service.csv.answer;

import acropollis.municipali.omega.admin.data.dto.statistics.csv.UserAnswerStatisticsCsvRow;

import java.util.List;

public interface AdminCsvAnswerService {
    String produce(List<UserAnswerStatisticsCsvRow> rows);
}
