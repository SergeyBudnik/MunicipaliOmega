package acropollis.municipali.omega.user.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.views.ArticleView;

import java.util.Collection;

public interface ArticleRestService {
    Collection<Article> getAllArticles();
    void addArticleView(ArticleView articleView);
}
