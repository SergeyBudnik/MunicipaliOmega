package acropollis.municipali.omega.user.cache.article.visible;

import acropollis.municipali.omega.common.dto.article.Article;

import java.util.Collection;
import java.util.Optional;

public interface VisibleArticlesCache {
    Optional<Article> getArticle(long articleId);
    Collection<Article> getArticles();
    void setArticles(Collection<Article> articles);
}
