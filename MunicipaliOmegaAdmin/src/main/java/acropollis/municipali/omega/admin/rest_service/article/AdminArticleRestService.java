package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;

import java.util.Collection;

public interface AdminArticleRestService {
    Collection<Article> getAllArticles(MunicipaliUserInfo userInfo);
    Article getArticle(MunicipaliUserInfo userInfo, long id);
    byte [] getArticleIcon(MunicipaliUserInfo userInfo, long id, int size);
    byte [] getAnswerIcon(MunicipaliUserInfo userInfo, long articleId, long questionId, long answerId, int size);
    long createArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon);
    void updateArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon);
    void deleteArticle(MunicipaliUserInfo userInfo, long id);
}
