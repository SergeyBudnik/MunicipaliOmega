package acropollis.municipali.omega.user.cache.article.all;

import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;

import java.util.Collection;

public interface UserAllArticlesCache {
    Collection<ArticleWithIcon> getAllArticles();
    void addArticle(ArticleWithIcon article);
    void removeArticle(long id);
}
