package acropollis.municipali.omega.admin.data.dto.article.question.answer;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.beans.Transient;
import java.util.Map;

@Data
public class AnswerWithIcon {
    private Long id;
    private Map<Language, TranslatedAnswer> translatedAnswer;
    private Map<Integer, byte []> icon;

    @Transient
    public Answer withoutIcon() {
        Answer answer = new Answer();

        answer.setId(id);
        answer.setTranslatedAnswer(translatedAnswer);

        return answer;
    }
}
