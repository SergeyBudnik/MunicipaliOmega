package acropollis.municipali.omega.user.rest_service.answer;

import acropollis.municipali.omega.user.data.dto.answer.UserAnswer;

import java.util.Map;

public interface AnswerRestService {
    Map<Long, Long> getAnswerStatistics(long articleId, long questionId);
    void answer(UserAnswer answer);
}
