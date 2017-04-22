package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.push.ArticleToReleasePushRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleToReleasePushRecordDao extends JpaRepository<ArticleToReleasePushRecordModel, Long> {
    List<ArticleToReleasePushRecordModel> findByReleaseDateLessThan(long to);
}
