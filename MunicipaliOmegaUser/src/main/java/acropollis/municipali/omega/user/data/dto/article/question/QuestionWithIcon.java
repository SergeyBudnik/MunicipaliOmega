package acropollis.municipali.omega.user.data.dto.article.question;

import acropollis.municipali.omega.common.dto.article.question.QuestionAnswerType;
import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.user.data.dto.article.question.answer.AnswerWithIcon;
import lombok.Data;

import java.beans.Transient;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class QuestionWithIcon {
    private Long id;
    private QuestionAnswerType answerType;
    private Map<Language, TranslatedQuestion> translatedQuestion;
    private List<AnswerWithIcon> answers;
    private boolean showResult;

    @Transient
    public Question withoutIcon() {
        Question question = new Question();

        question.setId(id);
        question.setAnswerType(answerType);
        question.setShowResult(showResult);
        question.setTranslatedQuestion(translatedQuestion);
        question.setAnswers(answers.stream().map(it -> it != null ? it.withoutIcon() : null).collect(Collectors.toList()));

        return question;
    }
}
