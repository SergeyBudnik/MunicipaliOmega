package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.exceptions.HttpEntityIllegalStateException;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Service
@Qualifier(Qualifiers.MODEL_VALIDATION)
public class AdminArticleModelValidationRestServiceImpl implements AdminArticleRestService {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private AdminArticleRestService adminArticleRestService;

    @Autowired
    private ArticleService articleService;

    @Override
    public Collection<Article> getAllArticles(CustomerInfo user) {
        return adminArticleRestService.getAllArticles(user);
    }

    @Override
    public Article getArticle(CustomerInfo user, long id) {
        return adminArticleRestService.getArticle(user, id);
    }

    @Override
    public byte [] getArticleIcon(CustomerInfo user, long id, int size) {
        return adminArticleRestService.getArticleIcon(user, id, size);
    }

    @Override
    public byte [] getAnswerIcon(CustomerInfo user, long articleId, long questionId, long answerId, int size) {
        return adminArticleRestService.getAnswerIcon(user, articleId, questionId, answerId, size);
    }

    @Override
    public long createArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        return adminArticleRestService.createArticle(user, articleWithIcon);
    }

    @Override
    @Transactional(readOnly = true)
    public void updateArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
//        Article article = articleService
//                .get(articleWithIcon.getId())
//                .orElseThrow(() -> new HttpEntityNotFoundException(""));
//
//        boolean isReleased = article.getReleaseDate() <= new Date().getTime();
//
//        if (isReleased) {
//            throw new HttpEntityIllegalStateException("");
//        }

        adminArticleRestService.updateArticle(user, articleWithIcon);
    }

    @Override
    @Transactional
    public void deleteArticle(CustomerInfo user, long id) {
        adminArticleRestService.deleteArticle(user, id);
    }
}
