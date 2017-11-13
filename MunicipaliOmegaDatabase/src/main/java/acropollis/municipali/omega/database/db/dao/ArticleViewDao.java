package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.views.ArticleViewIdModel;
import acropollis.municipali.omega.database.db.model.views.ArticleViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleViewDao extends JpaRepository<ArticleViewModel, ArticleViewIdModel> {
    @Query("select count(*) from ArticleViewModel a where a.id.articleId = :articleId")
    long countArticleViewsByArticleId(@Param("articleId") long articleId);
}
