package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.answer.UserAnswerModel;
import acropollis.municipali.omega.database.db.model.answer.UserAnswerModelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerDao extends JpaRepository<UserAnswerModel, UserAnswerModelId> {
    @Query(
            "select count(*) from UserAnswerModel a where " +
            "a.id.articleId = :articleId and " +
            "a.id.questionId = :questionId and " +
            "a.answerId = :answerId"
    )
    long countAnswersAmount(
            @Param("articleId") long articleId,
            @Param("questionId") long questionId,
            @Param("answerId") long answerId
    );
}
