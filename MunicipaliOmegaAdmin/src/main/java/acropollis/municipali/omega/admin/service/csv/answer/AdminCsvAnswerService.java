package acropollis.municipali.omega.admin.service.csv.answer;

import acropollis.municipali.omega.common.dto.answer.UserAnswer;

import java.util.List;

public interface AdminCsvAnswerService {
    String produce(List<UserAnswer> userAnswers);
}
