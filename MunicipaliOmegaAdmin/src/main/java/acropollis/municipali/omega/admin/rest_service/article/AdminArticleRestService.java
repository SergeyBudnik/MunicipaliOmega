package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;

import java.util.Collection;

public interface AdminArticleRestService {
    Collection<Article> getAllArticles(CustomerInfo user);
    Article getArticle(CustomerInfo user, long id);
    byte [] getArticleIcon(CustomerInfo user, long id, int size);
    byte [] getAnswerIcon(CustomerInfo user, long articleId, long questionId, long answerId, int size);
    long createArticle(CustomerInfo user, ArticleWithIcon articleWithIcon);
    void updateArticle(CustomerInfo user, ArticleWithIcon articleWithIcon);
    void deleteArticle(CustomerInfo user, long id);
}
