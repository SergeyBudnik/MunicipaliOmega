package acropollis.municipali.omega.database.db.model.answer;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "USER_ANSWER")
public class UserAnswerModel {
    @EmbeddedId
    @Delegate
    private UserAnswerModelId id;
    @Column(name = "ANSWER_ID")
    private long answerId;
}
