package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import acropollis.municipali.omega.database.db.service.article.ArticleService;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminArticleModelRestServiceImpl implements AdminArticleRestService {
    @Autowired
    private ArticleService articleService;

    @Transactional(readOnly = true)
    @Override
    public Collection<Article> getAllArticles(MunicipaliUserInfo userInfo) {
        return articleService.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Article getArticle(MunicipaliUserInfo userInfo, long id) {
        return articleService
                .get(id)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional
    @Override
    public long createArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon) {
        return articleService.create(articleWithIcon);
    }

    @Transactional
    @Override
    public void updateArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon) {
        try {
            articleService.update(articleWithIcon);
        } catch (EntityDoesNotExist e) {
            throw new HttpEntityNotFoundException("");
        }
    }

    @Transactional
    @Override
    public void deleteArticle(MunicipaliUserInfo userInfo, long id) {
        try {
            articleService.delete(id);
        } catch (EntityDoesNotExist e) {
            throw new HttpEntityNotFoundException("");
        }
    }
}
