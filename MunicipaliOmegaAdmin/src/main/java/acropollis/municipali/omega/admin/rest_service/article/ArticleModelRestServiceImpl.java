package acropollis.municipali.omega.admin.rest_service.article;

import acropollis.municipali.omega.admin.data.converters.article.ArticleDtoConverter;
import acropollis.municipali.omega.admin.data.converters.article.ArticleModelConverter;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import acropollis.municipali.omega.common.dto.article.question.answer.AnswerWithIcon;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.database.db.model.article.question.QuestionModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.AnswerModel;
import acropollis.municipali.omega.common.exceptions.EntityNotFoundException;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.service.image.entity.EntityImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
@Qualifier(Qualifiers.MODEL)
public class ArticleModelRestServiceImpl implements ArticleRestService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private EntityImageStorageService entityImageStorageService;

    @Transactional(readOnly = true)
    @Override
    public Collection<Article> getAllArticles(CustomerInfo user) {
        return articleDao
                .findByIsDeleted(false)
                .stream()
                .map(ArticleModelConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Article getArticle(CustomerInfo user, long id) {
        ArticleModel articleModel = articleDao.findOneByIdAndIsDeleted(id, false);

        if (articleModel == null) {
            throw new EntityNotFoundException("");
        }

        return ArticleModelConverter.convert(articleModel);
    }

    @Transactional(readOnly = true)
    @Override
    public byte [] getArticleIcon(CustomerInfo user, long id, int size) {
        ArticleModel article = articleDao.findOneByIdAndIsDeleted(id, false);

        if (article == null) {
            throw new EntityNotFoundException("");
        }

        Optional<byte []> icon = entityImageStorageService.getIcon(config.getString("images.articles"), id, size);

        if (icon.isPresent()) {
            return icon.get();
        } else {
            throw new EntityNotFoundException("");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public byte [] getAnswerIcon(CustomerInfo user, long articleId, long questionId, long answerId, int size) {
        ArticleModel article = articleDao.findOneByIdAndIsDeleted(articleId, false);

        if (article == null) {
            throw new EntityNotFoundException("");
        }

        Optional<QuestionModel> question = article.getQuestions().stream().filter(it -> it.getId() == questionId).findAny();

        if (!question.isPresent()) {
            throw new EntityNotFoundException("");
        }

        Optional<AnswerModel> answer = question.get().getAnswers().stream().filter(it -> it.getId() == answerId).findAny();

        if (!answer.isPresent()) {
            throw new EntityNotFoundException("");
        }

        Optional<byte []> icon = entityImageStorageService.getIcon(config.getString("images.answers"), answerId, size);

        if (icon.isPresent()) {
            return icon.get();
        } else {
            throw new EntityNotFoundException("");
        }
    }

    @Transactional(readOnly = false)
    @Override
    public long createArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        ArticleModel articleModel = articleDao.save(ArticleDtoConverter.convert(articleWithIcon.withoutIcon(), false));

        saveIcons(articleModel, articleWithIcon);

        return articleModel.getId();
    }

    @Transactional(readOnly = false)
    @Override
    public void updateArticle(CustomerInfo user, ArticleWithIcon articleWithIcon) {
        ArticleModel oldArticleModel = articleDao.findOneByIdAndIsDeleted(articleWithIcon.getId(), false);

        if (oldArticleModel == null) {
            throw new EntityNotFoundException("");
        }

        ArticleModel newArticleModel = articleDao.save(ArticleDtoConverter.convert(articleWithIcon.withoutIcon(), false));

        clearIcons(oldArticleModel);
        saveIcons(newArticleModel, articleWithIcon);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteArticle(CustomerInfo user, long id) {
        ArticleModel articleModel = articleDao.findOneByIdAndIsDeleted(id, false);

        if (articleModel == null) {
            throw new EntityNotFoundException("");
        }

        clearIcons(articleModel);

        articleModel.setDeleted(true);
        articleModel.getQuestions().clear();
        articleModel.getTranslatedArticles().clear();
    }

    private void saveIcons(ArticleModel articleModel, ArticleWithIcon articleWithIcon) {
        if (!articleWithIcon.getIcon().isEmpty()) {
            entityImageStorageService.saveImages(config.getString("images.articles"), articleModel.getId(), articleWithIcon.getIcon());
        }

        int questionOrder = 0;

        for (QuestionWithIcon question : articleWithIcon.getQuestions()) {
            int currentQuestionOrder = questionOrder;

            QuestionModel questionModel = articleModel
                    .getQuestions()
                    .stream()
                    .filter(it -> it.getOrder() == currentQuestionOrder)
                    .findAny()
                    .get();

            int answerOrder = 0;

            for (AnswerWithIcon answerWithIcon : question.getAnswers()) {
                if (answerWithIcon != null) {
                    int currentAnswerOrder = answerOrder;

                    AnswerModel answerModel = questionModel
                            .getAnswers()
                            .stream()
                            .filter(it -> it != null)
                            .filter(it -> it.getOrder() == currentAnswerOrder)
                            .findAny()
                            .get();

                    if (!answerWithIcon.getIcon().isEmpty()) {
                        entityImageStorageService.saveImages(config.getString("images.answers"), answerModel.getId(), answerWithIcon.getIcon());
                    }
                }

                answerOrder++;
            }

            questionOrder++;
        }
    }

    private void clearIcons(ArticleModel article) {
        entityImageStorageService.removeImages(config.getString("images.articles"), article.getId());

        article.getQuestions().forEach(question ->
                question.getAnswers().forEach(answer ->
                        entityImageStorageService.removeImages(config.getString("images.answers"), answer.getId()))
        );
    }
}
