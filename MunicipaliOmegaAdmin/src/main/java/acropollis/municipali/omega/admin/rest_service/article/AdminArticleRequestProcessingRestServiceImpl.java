package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static acropollis.municipali.omega.common.utils.ImageUtils.*;

@Service
@Qualifier(Qualifiers.REQUEST_PROCESSING)
public class AdminArticleRequestProcessingRestServiceImpl implements AdminArticleRestService {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private AdminArticleRestService adminArticleRestService;

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
    public byte[] getAnswerIcon(CustomerInfo user, long articleId, long questionId, long answerId, int size) {
        return adminArticleRestService.getAnswerIcon(user, articleId, questionId, answerId, size);
    }

    @Override
    public long createArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        processIcons(articleWithIcon);

        return adminArticleRestService.createArticle(user, articleWithIcon);
    }

    @Override
    public void updateArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        processIcons(articleWithIcon);

        adminArticleRestService.updateArticle(user, articleWithIcon);
    }

    @Override
    public void deleteArticle(CustomerInfo user, long id) {
        adminArticleRestService.deleteArticle(user, id);
    }

    private void processIcons(ArticleWithIcon articleWithIcon) {
        articleWithIcon.setIcon(processIcons(articleWithIcon.getIcon().get(-1)));

        articleWithIcon
                .getQuestions()
                .forEach(question ->
                        question
                                .getAnswers()
                                .stream()
                                .filter(it -> it != null)
                                .forEach(answer ->
                                        answer.setIcon(processIcons(answer.getIcon().get(-1)))
                                )
        );
    }

    private Map<Integer, byte []> processIcons(byte [] iconBytes) {
        Map<Integer, byte []> icon = new HashMap<>();

        if (iconBytes != null) {
            try {
                for (int size : new Integer [] {100, 200, 300, 400, 500}) {
                    icon.put(size, toBytes(scaleImageByWidth(fromBytes(iconBytes), size)));
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        return icon;
    }
}