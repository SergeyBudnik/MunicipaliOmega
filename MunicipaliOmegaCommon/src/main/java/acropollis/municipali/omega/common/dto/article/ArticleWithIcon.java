package acropollis.municipali.omega.common.dto.article;

import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.beans.Transient;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ArticleWithIcon {
    private Long id;
    private ArticleType type;
    private Map<Language, TranslatedArticle> translatedArticle;
    private List<QuestionWithIcon> questions;
    private Map<Integer, byte []> icon;
    private Map<Integer, byte []> image;
    private Map<Integer, byte []> clippedImage;
    private String video;
    private boolean sendPushOnRelease;
    private long creationDate;
    private long releaseDate;
    private long calendarStartDate;
    private long calendarFinishDate;
    private long expirationDate;
    private long lastUpdateDate;

    @Transient
    public Article withoutIcon() {
        Article article = new Article();

        article.setId(id);
        article.setType(type);
        article.setTranslatedArticle(translatedArticle);
        article.setQuestions(questions.stream().map(QuestionWithIcon::withoutIcon).collect(Collectors.toList()));
        article.setVideo(video);
        article.setSendPushOnRelease(sendPushOnRelease);
        article.setCreationDate(creationDate);
        article.setReleaseDate(releaseDate);
        article.setExpirationDate(expirationDate);
        article.setCalendarStartDate(calendarStartDate);
        article.setCalendarFinishDate(calendarFinishDate);
        article.setLastUpdateDate(lastUpdateDate);

        return article;
    }
}
