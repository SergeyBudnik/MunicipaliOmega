package acropollis.municipali.omega.user.data.dto.article.question;

import acropollis.municipali.omega.common.dto.article.question.QuestionAnswerType;
import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.user.data.dto.article.question.answer.Answer;
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
    private boolean showResult;
    private Map<Language, TranslatedQuestion> translatedQuestion;
    private List<Answer> answers;

    @Transient
    public QuestionWithIcon withIcon(Map<Long, Map<Integer, byte []>> icons) {
        QuestionWithIcon questionWithIcon = new QuestionWithIcon();

        questionWithIcon.setId(id);
        questionWithIcon.setAnswerType(answerType);
        questionWithIcon.setTranslatedQuestion(translatedQuestion);
        questionWithIcon.setAnswers(
                answers
                        .stream()
                        .map(it -> it != null ? it.withIcon(icons.getOrDefault(it.getId(), new HashMap<>())) : null)
                        .collect(Collectors.toList())
        );
        questionWithIcon.setShowResult(showResult);

        return questionWithIcon;
    }
}
