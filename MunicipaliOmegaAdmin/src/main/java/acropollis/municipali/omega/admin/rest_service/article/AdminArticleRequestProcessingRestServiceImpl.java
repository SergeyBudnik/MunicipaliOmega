package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

import static acropollis.municipali.omega.common.utils.common.ImageUtils.resizeImages;

@Qualifier(Qualifiers.REQUEST_PROCESSING)
@Service
public class AdminArticleRequestProcessingRestServiceImpl implements AdminArticleRestService {
    @Autowired
    @Qualifier(Qualifiers.MODEL_VALIDATION)
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
    public byte[] getAnswerIcon(MunicipaliUserInfo userInfo, long articleId, long questionId, long answerId, int size) {
        return adminArticleRestService.getAnswerIcon(userInfo, articleId, questionId, answerId, size);
    }

    @Override
    public long createArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon) {
        processIcons(articleWithIcon);

        return adminArticleRestService.createArticle(userInfo, articleWithIcon);
    }

    @Override
    public void updateArticle(MunicipaliUserInfo userInfo, ArticleWithIcon articleWithIcon) {
        processIcons(articleWithIcon);

        adminArticleRestService.updateArticle(userInfo, articleWithIcon);
    }

    @Override
    public void deleteArticle(MunicipaliUserInfo userInfo, long id) {
        adminArticleRestService.deleteArticle(userInfo, id);
    }

    private void processIcons(ArticleWithIcon articleWithIcon) {
        articleWithIcon.setIcon(resizeImages(
                articleWithIcon.getIcon().get(-1),
                100, 200, 300, 400, 500
        ));

        articleWithIcon
                .getQuestions()
                .forEach(question ->
                        question
                                .getAnswers()
                                .stream()
                                .filter(Objects::nonNull)
                                .forEach(answer ->
                                        answer.setIcon(resizeImages(
                                                answer.getIcon().get(-1),
                                                100, 200, 300, 400, 500
                                        ))
                                )
        );
    }
}
