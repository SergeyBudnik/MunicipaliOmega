package acropollis.municipali.omega.database.db.service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import acropollis.municipali.omega.common.dto.article.question.answer.AnswerWithIcon;
import acropollis.municipali.omega.database.db.converters.article.ArticleDtoConverter;
import acropollis.municipali.omega.database.db.converters.article.ArticleModelConverter;
import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.dao.ArticleToReleasePushRecordDao;
import acropollis.municipali.omega.database.db.exceptions.EntityDoesNotExist;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.database.db.model.article.question.QuestionModel;
import acropollis.municipali.omega.database.db.model.article.question.answer.AnswerModel;
import acropollis.municipali.omega.database.db.model.push.ArticleToReleasePushRecordModel;
import acropollis.municipali.omega.database.db.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.config.PropertiesConfig.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleToReleasePushRecordDao articleToReleasePushRecordDao;

    @Autowired
    private ImageService imageService;

    @Override
    public List<Article> getAll() {
        return articleDao
                .findByIsDeletedIsFalse()
                .stream()
                .map(it -> ArticleModelConverter.convert(it, false))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> get(long articleId) {
        return Optional
                .ofNullable(articleDao.findOneByIdAndIsDeletedIsFalse(articleId))
                .map(it -> ArticleModelConverter.convert(it, true));
    }

    @Override
    public long create(ArticleWithIcon articleWithIcon) {
        ArticleModel articleModel = articleDao.save(ArticleDtoConverter.convert(articleWithIcon.withoutIcon(), false));

        ArticleToReleasePushRecordModel articleToReleasePushRecordModel = new ArticleToReleasePushRecordModel(); {
            articleToReleasePushRecordModel.setArticleId(articleModel.getId());
        }

        if (articleModel.isSendPushOnRelease()) {
            articleToReleasePushRecordDao.save(articleToReleasePushRecordModel);
        }

        saveIcons(articleModel, articleWithIcon);

        return articleModel.getId();
    }

    @Override
    public void update(ArticleWithIcon article) throws EntityDoesNotExist {
        ArticleModel oldArticle = Optional
                .ofNullable(articleDao.findOneByIdAndIsDeletedIsFalse(article.getId()))
                .orElseThrow(EntityDoesNotExist::new);

        ArticleModel newArticle = articleDao.save(ArticleDtoConverter.convert(article.withoutIcon(), false));

        clearIcons(oldArticle);
        saveIcons(newArticle, article);
    }

    @Override
    public void delete(long articleId) throws EntityDoesNotExist {
        ArticleModel articleModel = articleDao.findOneByIdAndIsDeletedIsFalse(articleId);

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
        imageService.removeAllImagesRemoveDirectory(
                config.getImagesArticlesIconsLocation().getValue(),
                String.format("%d", article.getId())
        );

        imageService.removeAllImagesRemoveDirectory(
                config.getImagesArticlesImagesLocation().getValue(),
                String.format("%d", article.getId())
        );

        imageService.removeAllImagesRemoveDirectory(
                config.getImagesArticlesClippedImagesLocation().getValue(),
                String.format("%d", article.getId())
        );

        article.getQuestions().forEach(question ->
                question.getAnswers().forEach(answer ->
                        imageService.removeAllImagesRemoveDirectory(
                                config.getImagesAnswersIconsLocation().getValue(),
                                String.format("%d", answer.getId())
                        )
                )
        );
    }

    private void saveIcons(ArticleModel articleModel, ArticleWithIcon articleWithIcon) {
        articleWithIcon.getIcon().forEach((size, icon) ->
                imageService.addImage(
                        config.getImagesArticlesIconsLocation().getValue(),
                        String.format("%d", articleModel.getId()),
                        String.format("%dx%d", size, size),
                        icon
                )
        );

        articleWithIcon.getImage().forEach((size, icon) ->
                imageService.addImage(
                        config.getImagesArticlesImagesLocation().getValue(),
                        String.format("%d", articleModel.getId()),
                        String.format("%dx%d", size, size),
                        icon
                )
        );

        articleWithIcon.getClippedImage().forEach((size, icon) ->
                imageService.addImage(
                        config.getImagesArticlesClippedImagesLocation().getValue(),
                        String.format("%d", articleModel.getId()),
                        String.format("%dx%d", size.getX(), size.getY()),
                        icon
                )
        );

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
                            .filter(Objects::nonNull)
                            .filter(it -> it.getOrder() == currentAnswerOrder)
                            .findAny()
                            .get();

                    answerWithIcon.getIcon().forEach((size, icon) ->
                            imageService.addImage(
                                    config.getImagesAnswersIconsLocation().getValue(),
                                    String.format("%d", answerModel.getId()),
                                    String.format("%dx%d", size, size),
                                    icon
                            )
                    );
                }

                answerOrder++;
            }

            questionOrder++;
        }
    }
}
