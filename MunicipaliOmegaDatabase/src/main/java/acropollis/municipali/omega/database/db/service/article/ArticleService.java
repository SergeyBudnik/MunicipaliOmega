package acropollis.municipali.omega.database.db.service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> getAll();
    Optional<Article> get(long articleId);
    Optional<byte []> getIcon(long articleId, int size);
    Optional<byte []> getImage(long articleId, int size);
    long create(ArticleWithIcon articleWithIcon);
    void update(ArticleWithIcon article) throws EntityDoesNotExist;
    void delete(long articleId) throws EntityDoesNotExist;
}
