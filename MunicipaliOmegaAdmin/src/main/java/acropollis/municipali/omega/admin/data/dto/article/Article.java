package acropollis.municipali.omega.admin.data.dto.article;

import acropollis.municipali.omega.admin.data.dto.article.question.Question;
import acropollis.municipali.omega.common.dto.article.ArticleType;
import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Article {
    private Long id;
    private ArticleType type;
    private Map<Language, TranslatedArticle> translatedArticle;
    private List<Question> questions;
    private boolean sendPushOnRelease;
    private long creationDate;
    private long releaseDate;
    private long expirationDate;
    private long lastUpdateDate;
}
