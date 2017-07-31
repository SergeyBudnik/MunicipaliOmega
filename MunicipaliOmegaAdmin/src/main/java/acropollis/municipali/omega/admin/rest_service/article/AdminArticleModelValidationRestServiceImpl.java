package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Qualifier(Qualifiers.MODEL_VALIDATION)
public class AdminArticleModelValidationRestServiceImpl implements AdminArticleRestService {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private AdminArticleRestService adminArticleRestService;

    @Override
    public Collection<Article> getAllArticles(MunicipaliUserInfo userInfo) {
        return adminArticleRestService.getAllArticles(userInfo);
    }

    @Override
    public Article getArticle(MunicipaliUserInfo userInfo, long id) {
        return adminArticleRestService.getArticle(userInfo, id);
    }

    @Override
    public byte [] getArticleIcon(MunicipaliUserInfo userInfo, long id, int size) {
        return adminArticleRestService.getArticleIcon(userInfo, id, size);
    }

    @Override
    public byte [] getAnswerIcon(MunicipaliUserInfo userInfo, long articleId, long questionId, long answerId, int size) {
        return adminArticleRestService.getAnswerIcon(userInfo, articleId, questionId, answerId, size);
    }

    @Override
    public long createArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon) {
        return adminArticleRestService.createArticle(userInfo, articleWithIcon);
    }

    @Override
    @Transactional
    public void updateArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon) {
        adminArticleRestService.updateArticle(userInfo, articleWithIcon);
    }

    @Override
    @Transactional
    public void deleteArticle(MunicipaliUserInfo userInfo, long id) {
        adminArticleRestService.deleteArticle(userInfo, id);
    }
}
