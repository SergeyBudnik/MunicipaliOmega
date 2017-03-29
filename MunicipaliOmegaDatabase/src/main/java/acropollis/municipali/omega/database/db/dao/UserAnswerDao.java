package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerDao extends JpaRepository<UserAnswerModel, UserAnswerModelId> {
    long countByIdArticleIdAndIdQuestionIdAndAnswerId(long articleId, long questionId, long answerId);
}
