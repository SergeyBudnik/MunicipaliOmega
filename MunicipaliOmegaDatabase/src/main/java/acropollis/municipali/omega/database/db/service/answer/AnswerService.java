package acropollis.municipali.omega.database.db.service.answer;

import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;

import java.util.Optional;

public interface AnswerService {
    Optional<Answer> get(Question question, long answerId);
    Optional<byte []> getIcon(Question question, long answerId, int size);
}
