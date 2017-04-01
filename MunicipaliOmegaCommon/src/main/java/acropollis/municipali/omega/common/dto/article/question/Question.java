package acropollis.municipali.omega.common.dto.article.question;

import acropollis.municipali.omega.common.dto.article.question.QuestionAnswerType;
import acropollis.municipali.omega.common.dto.article.question.TranslatedQuestion;
import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Question {
    private Long id;
    private QuestionAnswerType answerType;
    private Map<Language, TranslatedQuestion> translatedQuestion;
    private List<Answer> answers;
    private boolean showResult;
}
