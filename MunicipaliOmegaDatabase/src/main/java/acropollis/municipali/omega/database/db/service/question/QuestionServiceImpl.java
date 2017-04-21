package acropollis.municipali.omega.database.db.service.question;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Override
    public Optional<Question> get(Article article, long questionId) {
        return article
                .getQuestions()
                .stream()
                .filter(it -> it.getId().equals(questionId))
                .findAny();
    }
}
