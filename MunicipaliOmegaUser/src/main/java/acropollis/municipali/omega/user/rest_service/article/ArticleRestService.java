package acropollis.municipali.omega.user.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;

import java.util.Collection;

public interface ArticleRestService {
    Collection<Article> getAllArticles();
    byte [] getArticleIcon(long articleId, int size);
    byte [] getAnswerIcon(long articleId, long questionId, long answerId, int size);
}
