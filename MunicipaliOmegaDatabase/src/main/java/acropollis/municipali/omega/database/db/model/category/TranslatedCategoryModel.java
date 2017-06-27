package acropollis.municipali.omega.database.db.model.category;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TRANSLATED_ARTICLE")
public class TranslatedCategoryModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryModel category;

    @Column(name = "LANGUAGE")
    private Language language;

    @Column(name = "TITLE", length = 128)
    private String title;

    @Column(name = "TEXT", length = 8192)
    private String text;
}
