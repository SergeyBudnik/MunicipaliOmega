package acropollis.municipali.omega.user.cache.article.all;

import acropollis.municipali.omega.user.data.dto.article.ArticleWithIcon;

import java.util.Collection;

public interface AllArticlesCache {
    Collection<ArticleWithIcon> getAllArticles();
    void addArticle(ArticleWithIcon article);
    void removeArticle(long id);
}
