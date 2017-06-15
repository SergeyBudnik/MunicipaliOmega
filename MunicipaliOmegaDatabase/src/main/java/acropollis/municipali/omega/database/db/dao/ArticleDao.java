package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleDao extends JpaRepository<ArticleModel, Long> {
    ArticleModel findOneByIdAndIsDeletedIsFalse(long id);
    List<ArticleModel> findByIsDeletedIsFalse();
    List<ArticleModel> findByIsDeletedIsFalseAndReleaseDateLessThanAndExpirationDateGreaterThan(
            long releaseDate,
            long expirationDate
    );
}
