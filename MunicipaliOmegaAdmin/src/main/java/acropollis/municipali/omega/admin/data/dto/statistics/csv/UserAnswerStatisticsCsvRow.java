package acropollis.municipali.omega.admin.data.dto.statistics.csv;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.dto.user.User;
import lombok.Data;

import java.util.Optional;

@Data
public class UserAnswerStatisticsCsvRow {
    private Optional<User> user;
    private Article article;
    private Question question;
    private Answer answer;
}
