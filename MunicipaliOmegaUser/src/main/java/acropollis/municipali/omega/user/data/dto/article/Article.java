package acropollis.municipali.omega.user.data.dto.article;

import acropollis.municipali.omega.common.dto.article.ArticleType;
import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.user.data.dto.article.question.Question;
import lombok.Data;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Article {
    private Long id;
    private ArticleType type;
    private Map<Language, TranslatedArticle> translatedArticle;
    private List<Question> questions;
    private boolean sendPushOnRelease;
    private long releaseDate;
    private long expirationDate;
    private long lastUpdateDate;

    @Transient
    public ArticleWithIcon withIcon(
            Map<Integer, byte []> articleIcon,
            Map<Long, Map<Long, Map<Integer, byte []>>> answersIcons) {

        ArticleWithIcon articleWithIcon = new ArticleWithIcon();

        articleWithIcon.setId(id);
        articleWithIcon.setType(type);
        articleWithIcon.setIcon(articleIcon);
        articleWithIcon.setTranslatedArticle(translatedArticle);
        articleWithIcon.setSendPushOnRelease(sendPushOnRelease);
        articleWithIcon.setQuestions(
                questions
                        .stream()
                        .map(it -> it.withIcon(answersIcons.getOrDefault(it.getId(), new HashMap<>())))
                        .collect(Collectors.toList())
        );
        articleWithIcon.setReleaseDate(releaseDate);
        articleWithIcon.setExpirationDate(expirationDate);
        articleWithIcon.setLastUpdateDate(lastUpdateDate);

        return articleWithIcon;
    }
}
