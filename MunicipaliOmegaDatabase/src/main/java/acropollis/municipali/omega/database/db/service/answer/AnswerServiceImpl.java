package acropollis.municipali.omega.database.db.service.answer;

import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {
    public Optional<Answer> get(Question question, long answerId) {
        return question
                .getAnswers()
                .stream()
                .filter(it -> it.getId().equals(answerId))
                .findAny();
    }
}
