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
import acropollis.municipali.omega.database.db.utils.log.LogUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;
import static java.lang.String.format;

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
        long t1 = System.currentTimeMillis();

        ArticleModel oldArticle = Optional
                .ofNullable(articleDao.findOneByIdAndIsDeletedIsFalse(article.getId()))
                .orElseThrow(EntityDoesNotExist::new);

        ArticleModel newArticle = articleDao
                .save(ArticleDtoConverter.convert(article.withoutIcon(), false));

        long t2 = System.currentTimeMillis();

        LogUtils
                .getArticlesServiceDatabaseLogger()
                .info(format("Article update - id: %d, duration: %d", article.getId(), t2 - t1));

        long t3 = System.currentTimeMillis();

        clearIcons(oldArticle);

        long t4 = System.currentTimeMillis();

        saveIcons(newArticle, article);

        long t5 = System.currentTimeMillis();

        LogUtils
                .getArticlesServiceImageHostingLogger()
                .info(format("Article images update - id: %d, duration: %d, %d",
                        article.getId(),
                        t4 - t3,
                        t5 - t4
                ));
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
        imageService.runInFtp(config.getImagesArticlesIconsLocation(), ftpClient -> {
            imageService.removeAllImagesRemoveDirectory(ftpClient, format("%d", article.getId()));
            imageService.removeAllImagesRemoveDirectory(ftpClient, format("%d", article.getId()));
            imageService.removeAllImagesRemoveDirectory(ftpClient, format("%d", article.getId()));

            article.getQuestions().forEach(question ->
                    question.getAnswers().forEach(answer ->
                            imageService.removeAllImagesRemoveDirectory(
                                    ftpClient,
                                    format("%d", answer.getId())
                            )
                    )
            );
        });
    }

    @SneakyThrows
    private void saveIcons(ArticleModel articleModel, ArticleWithIcon articleWithIcon) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Collection<Callable<Boolean>> imageUploaders = new ArrayList<>();

        articleWithIcon.getIcon().forEach((size, icon) ->
                imageUploaders.add(() -> {
                    imageService.addImage(
                            config.getImagesArticlesIconsLocation(),
                            format("%d", articleModel.getId()),
                            format("%dx%d", size, size),
                            icon
                    );

                    return true;
                })
        );

        articleWithIcon.getImage().forEach((size, icon) ->
                imageUploaders.add(() -> {
                    imageService.addImage(
                            config.getImagesArticlesImagesLocation(),
                            format("%d", articleModel.getId()),
                            format("%dx%d", size, size),
                            icon
                    );

                    return true;
                })
        );

        articleWithIcon.getClippedImage().forEach((size, icon) ->
                imageUploaders.add(() -> {
                    imageService.addImage(
                            config.getImagesArticlesClippedImagesLocation(),
                            format("%d", articleModel.getId()),
                            format("%dx%d", size.getX(), size.getY()),
                            icon
                    );

                    return true;
                })
        );

        int questionOrder = 0;

        for (QuestionWithIcon question : articleWithIcon.getQuestions()) {
            int currentQuestionOrder = questionOrder;

            QuestionModel questionModel = articleModel
                    .getQuestions()
                    .stream()
                    .filter(it -> it.getOrder() == currentQuestionOrder)
                    .findAny()
                    .orElseThrow(RuntimeException::new);

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
                            .orElseThrow(RuntimeException::new);

                    answerWithIcon.getIcon().forEach((size, icon) ->
                            imageUploaders.add(() -> {
                                imageService.addImage(
                                        config.getImagesAnswersIconsLocation(),
                                        format("%d", answerModel.getId()),
                                        format("%dx%d", size, size),
                                        icon
                                );

                                return true;
                            }
                    ));
                }

                answerOrder++;
            }

            questionOrder++;
        }

        executor.invokeAll(imageUploaders);
        executor.shutdown();
    }
}
