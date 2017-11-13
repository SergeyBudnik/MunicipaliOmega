package acropollis.municipali.omega.database.db.model.views;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ARTICLE_VIEW")
public class ArticleViewModel {
    @EmbeddedId
    @Delegate
    private ArticleViewIdModel id = new ArticleViewIdModel();
}
