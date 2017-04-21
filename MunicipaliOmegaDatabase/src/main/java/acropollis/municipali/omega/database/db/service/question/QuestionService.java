package acropollis.municipali.omega.database.db.service.question;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;

import java.util.Optional;

public interface QuestionService {
    Optional<Question> get(Article article, long questionId);
}
