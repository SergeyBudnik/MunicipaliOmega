package acropollis.municipali.omega.database.db.model.views;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Embeddable
public class ArticleViewIdModel implements Serializable {
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "ARTICLE_ID")
    private long articleId;
}
