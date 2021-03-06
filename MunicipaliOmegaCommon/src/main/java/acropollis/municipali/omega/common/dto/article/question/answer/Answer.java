package acropollis.municipali.omega.common.dto.article.question.answer;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.beans.Transient;
import java.util.Map;

@Data
public class Answer {
    private Long id;
    private Map<Language, TranslatedAnswer> translatedAnswer;
    private boolean hasIcon;
}
