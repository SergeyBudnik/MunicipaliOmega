package acropollis.municipali.omega.database.db.model.answer;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class UserAnswerModelId implements Serializable {
    @Column(name = "USER_AUTH_TOKEN")
    private String userAuthToken;
    @Column(name = "ARTICLE_ID")
    private long articleId;
    @Column(name = "QUESTION_ID")
    private long questionId;
}
