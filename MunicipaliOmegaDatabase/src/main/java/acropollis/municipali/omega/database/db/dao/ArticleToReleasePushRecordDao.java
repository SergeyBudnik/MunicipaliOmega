package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.push.ArticleToReleasePushRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleToReleasePushRecordDao extends JpaRepository<ArticleToReleasePushRecordModel, Long> {
    List<ArticleToReleasePushRecordModel> findByReleaseDateLessThan(long to);
    void deleteByArticleId(long articleId);
}
