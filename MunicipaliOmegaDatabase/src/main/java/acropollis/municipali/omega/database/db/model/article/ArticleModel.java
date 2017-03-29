package acropollis.municipali.omega.database.db.model.article;

import acropollis.municipali.omega.common.dto.article.ArticleType;
import acropollis.municipali.omega.database.db.model.article.question.QuestionModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "ARTICLE")
public class ArticleModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ARTICLE_TYPE")
    private ArticleType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TranslatedArticleModel> translatedArticles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<QuestionModel> questions;

    @Column(name = "SEND_PUSH_ON_RELEASE")
    private boolean sendPushOnRelease;

    @Column(name = "CREATION_DATE")
    private long creationDate;

    @Column(name = "RELEASE_DATE")
    private long releaseDate;

    @Column(name = "EXPIRATION_DATE")
    private long expirationDate;

    @Column(name = "LAST_UPDATE_DATE")
    private long lastUpdateDate;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @PrePersist
    @PreUpdate
    public void update() {
        if (creationDate <= 0) {
            creationDate = new Date().getTime();
        }

        lastUpdateDate = new Date().getTime();
    }
}
