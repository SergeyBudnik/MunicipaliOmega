package acropollis.municipali.omega.common.dto.article;

import acropollis.municipali.omega.common.dto.article.question.Question;
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
    private String video;
    private boolean sendPushOnRelease;
    private long creationDate;
    private long releaseDate;
    private long calendarStartDate;
    private long calendarFinishDate;
    private long expirationDate;
    private long lastUpdateDate;
}
