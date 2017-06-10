package acropollis.municipali.omega.database.db.model.category;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "CATEGORY")
public class CategoryModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TranslatedCategoryModel> translatedCategories;

    @Column(name = "IS_CLICKABLE")
    private boolean isClickable;

    @Column(name = "CREATION_DATE")
    private long creationDate;

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
