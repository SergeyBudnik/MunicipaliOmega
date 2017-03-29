package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<ArticleModel, Long> {
    List<ArticleModel> findByIsDeleted(boolean isDeleted);
    List<ArticleModel> findByLastUpdateDateGreaterThan(long lastUpdateDate);
    List<ArticleModel> findByReleaseDateLessThanEqualAndReleaseDateGreaterThanEqualAndIsDeleted(long from, long to, boolean isDeleted);
    ArticleModel findOneByIdAndIsDeleted(long id, boolean isDeleted);
}
