package acropollis.municipali.omega.admin.data.dto.article.question.answer;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.util.Map;

@Data
public class Answer {
    private Long id;
    private Map<Language, TranslatedAnswer> translatedAnswer;
}
