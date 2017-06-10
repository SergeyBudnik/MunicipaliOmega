package acropollis.municipali.omega.database.db.model.article;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TRANSLATED_ARTICLE")
public class TranslatedArticleCategoriesModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSLATED_ARTICLE_ID")
    private TranslatedArticleModel translatedArticle;

    @Column(name = "CATEGORY_TEXT", length = 64)
    private String text;
}
