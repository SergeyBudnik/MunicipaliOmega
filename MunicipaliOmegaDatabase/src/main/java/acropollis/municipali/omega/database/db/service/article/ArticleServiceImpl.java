package acropollis.municipali.omega.database.db.service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import acropollis.municipali.omega.common.dto.article.question.answer.AnswerWithIcon;
import acropollis.municipali.omega.common.utils.storage.EntityImageStorageUtils;
import acropollis.municipali.omega.common.utils.storage.SquareImageAdapter;
import acropollis.municipali.omega.database.db.converters.article.ArticleDtoConverter;
import acropollis.municipali.omega.database.db.converters.article.ArticleModelConverter;
import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.dao.ArticleToReleasePushRecordDao;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.database.db.model.article.question.QuestionModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.AnswerModel;
import acropollis.municipali.omega.database.db.model.push.ArticleToReleasePushRecordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleToReleasePushRecordDao articleToReleasePushRecordDao;

    @Override
    public List<Article> getAll() {
        return articleDao
                .findAll()
                .stream()
                .map(ArticleModelConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> get(long articleId) {
        return Optional
                .ofNullable(articleDao.findOneByIdAndIsDeleted(articleId, false))
                .map(ArticleModelConverter::convert);
    }

    @Override
    public Optional<byte[]> getIcon(long articleId, int size) {
        return EntityImageStorageUtils.getImage(config.getString("images.articles"), articleId, size, size);
    }

    @Override
    public long create(ArticleWithIcon articleWithIcon) {
        ArticleModel articleModel = articleDao.save(ArticleDtoConverter.convert(articleWithIcon.withoutIcon(), false));

        ArticleToReleasePushRecordModel articleToReleasePushRecordModel = new ArticleToReleasePushRecordModel(); {
            articleToReleasePushRecordModel.setArticleId(articleModel.getId());
        }

        articleToReleasePushRecordDao.save(articleToReleasePushRecordModel);

        saveIcons(articleModel, articleWithIcon);

        return articleModel.getId();
    }

    @Override
    public void update(ArticleWithIcon article) throws EntityDoesNotExist {
        Optional<ArticleModel> oldArticleOptional = Optional
                .ofNullable(articleDao.findOneByIdAndIsDeleted(article.getId(), false));

        if (!oldArticleOptional.isPresent()) {
            throw new EntityDoesNotExist();
        }

        ArticleModel newArticle = articleDao.save(ArticleDtoConverter.convert(article.withoutIcon(), false));

        clearIcons(oldArticleOptional.get());
        saveIcons(newArticle, article);
    }

    @Override
    public void delete(long articleId) throws EntityDoesNotExist {
        ArticleModel articleModel = articleDao.findOneByIdAndIsDeleted(articleId, false);

        if (articleModel == null) {
            throw new EntityDoesNotExist();
        }

        clearIcons(articleModel);

        articleModel.setDeleted(true);
        articleModel.getQuestions().clear();
        articleModel.getTranslatedArticles().clear();

        if (articleToReleasePushRecordDao.exists(articleId)) {
            articleToReleasePushRecordDao.delete(articleId);
        }
    }

    private void clearIcons(ArticleModel article) {
        EntityImageStorageUtils.removeImages(config.getString("images.articles"), article.getId());

        article.getQuestions().forEach(question ->
                        question.getAnswers().forEach(answer ->
                                EntityImageStorageUtils.removeImages(config.getString("images.answers"), answer.getId()))
        );
    }

    private void saveIcons(ArticleModel articleModel, ArticleWithIcon articleWithIcon) {
        if (!articleWithIcon.getIcon().isEmpty()) {
            EntityImageStorageUtils.saveImages(
                    config.getString("images.articles"),
                    articleModel.getId(),
                    SquareImageAdapter.pack(articleWithIcon.getIcon())
            );
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
                        EntityImageStorageUtils.saveImages(
                                config.getString("images.answers"),
                                answerModel.getId(),
                                SquareImageAdapter.pack(answerWithIcon.getIcon())
                        );
                    }
                }

                answerOrder++;
            }

            questionOrder++;
        }
    }
}
