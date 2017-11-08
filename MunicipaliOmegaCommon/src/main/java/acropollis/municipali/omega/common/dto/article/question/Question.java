package acropollis.municipali.omega.common.dto.article.question;

import acropollis.municipali.omega.common.dto.article.question.answer.Answer;
import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Question {
    private Long id;
    private QuestionAnswerType answerType;
    private Map<Language, TranslatedQuestion> translatedQuestion;
    private List<Answer> answers;
    private boolean showResult;
}
