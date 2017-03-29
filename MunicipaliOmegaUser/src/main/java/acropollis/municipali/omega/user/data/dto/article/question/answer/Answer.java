package acropollis.municipali.omega.user.data.dto.article.question.answer;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.beans.Transient;
import java.util.Map;

@Data
public class Answer {
    private Long id;
    private Map<Language, TranslatedAnswer> translatedAnswer;

    @Transient
    public AnswerWithIcon withIcon(Map<Integer, byte []> icon) {
        AnswerWithIcon answerWithIcon = new AnswerWithIcon();

        answerWithIcon.setId(id);
        answerWithIcon.setIcon(icon);
        answerWithIcon.setTranslatedAnswer(translatedAnswer);

        return answerWithIcon;
    }
}
