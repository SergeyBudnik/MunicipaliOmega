package acropollis.municipali.omega.user.cache.article.visible;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;

import java.util.Collection;
import java.util.Optional;

public interface VisibleArticlesCache {
    Optional<Article> getArticle(long articleId);
    Collection<Article> getArticles();
    Optional<byte []> getArticleIcon(long articleId, int size);
    Optional<byte []> getArticleImage(long articleId, int size);
    Optional<byte []> getAnswerIcon(long articleId, long questionId, long answerId, int size);
    void setArticles(Collection<ArticleWithIcon> articles);
}
