package acropollis.municipali.omega.user_notification.dto.article;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.util.Map;

@Data
public class Article {
    private Long id;
    private Map<Language, TranslatedArticle> translatedArticle;
}
