package acropollis.municipali.omega.common.dto.article.question;

import acropollis.municipali.omega.common.dto.article.question.answer.AnswerWithIcon;
import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        question.setAnswers(new ArrayList<>());

        for (AnswerWithIcon answer : answers) {
            question.getAnswers().add(
                    answer != null ?
                            answer.withoutIcon() :
                            null
            );
        }

        return question;
    }
}
