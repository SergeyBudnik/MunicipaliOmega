package acropollis.municipali.omega.database.db.model.views;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

@NoArgsConstructor
@Data
@Embeddable
public class ArticleViewModel {
    @EmbeddedId
    @Delegate
    private ArticleViewIdModel id = new ArticleViewIdModel();
}
