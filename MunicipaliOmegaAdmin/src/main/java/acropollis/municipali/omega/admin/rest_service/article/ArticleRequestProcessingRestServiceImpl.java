package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.admin.data.dto.article.Article;
import acropollis.municipali.omega.admin.data.dto.article.ArticleWithIcon;
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
public class ArticleRequestProcessingRestServiceImpl implements ArticleRestService {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private ArticleRestService articleRestService;

    @Override
    public Collection<Article> getAllArticles(CustomerInfo user) {
        return articleRestService.getAllArticles(user);
    }

    @Override
    public Article getArticle(CustomerInfo user, long id) {
        return articleRestService.getArticle(user, id);
    }

    @Override
    public byte [] getArticleIcon(CustomerInfo user, long id, int size) {
        return articleRestService.getArticleIcon(user, id, size);
    }

    @Override
    public byte[] getAnswerIcon(CustomerInfo user, long articleId, long questionId, long answerId, int size) {
        return articleRestService.getAnswerIcon(user, articleId, questionId, answerId, size);
    }

    @Override
    public long createArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        processIcons(articleWithIcon);

        return articleRestService.createArticle(user, articleWithIcon);
    }

    @Override
    public void updateArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        processIcons(articleWithIcon);

        articleRestService.updateArticle(user, articleWithIcon);
    }

    @Override
    public void deleteArticle(CustomerInfo user, long id) {
        articleRestService.deleteArticle(user, id);
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
