package acropollis.municipali.omega.database.db.model.article;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "TRANSLATED_ARTICLE")
public class TranslatedArticleModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    private ArticleModel article;

    @Column(name = "LANGUAGE")
    private Language language;

    @Column(name = "ARTICLE_TITLE", length = 128)
    private String title;

    @Column(name = "ARTICLE_TEXT", length = 4096)
    private String text;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "translatedArticle", cascade = CascadeType.ALL)
    private Collection<TranslatedArticleCategoriesModel> categories;
}
