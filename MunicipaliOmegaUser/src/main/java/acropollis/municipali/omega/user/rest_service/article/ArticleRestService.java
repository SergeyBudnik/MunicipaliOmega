package acropollis.municipali.omega.user.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;

import java.util.Collection;

public interface ArticleRestService {
    Collection<Article> getAllArticles();
}
