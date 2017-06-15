package acropollis.municipali.omega.user.async.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.Question;
import acropollis.municipali.omega.common.utils.storage.SquareImageAdapter;
import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;
import static acropollis.municipali.omega.common.utils.storage.EntityImageStorageUtils.getImages;
import static acropollis.municipali.omega.database.db.converters.article.ArticleModelConverter.convert;

@Service
public class VisibleArticlesReloadJob {
    @Autowired
    private VisibleArticlesCache visibleArticlesCache;

    @Autowired
    private ArticleDao articleDao;

    @Scheduled(fixedDelay = 5 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        long currentTime = new Date().getTime();

        List<ArticleWithIcon> visibleArticles = new ArrayList<>();

        List<ArticleModel> articlesModels = articleDao
                .findByIsDeletedIsFalseAndReleaseDateLessThanAndExpirationDateGreaterThan(
                        currentTime,
                        currentTime
                );

        for (ArticleModel articleModel : articlesModels) {
            Article article = convert(articleModel);

            ArticleWithIcon articleWithIcon = article.withIcon(
                    getArticleIcons(article),
                    getAnswersIcons(article)
            );

            visibleArticles.add(articleWithIcon);
        }

        visibleArticlesCache.setArticles(visibleArticles);
    }

    private Map<Integer, byte []> getArticleIcons(Article article) {
        return SquareImageAdapter.unpack(
                getImages(
                        config.getImagesArticlesIconsLocation().getValue(),
                        article.getId()
                )
                        .orElseGet(HashMap::new)
        );
    }

    private Map<Long, Map<Long, Map<Integer, byte []>>> getAnswersIcons(Article article) {
        Map<Long, Map<Long, Map<Integer, byte []>>> answersIcons = new HashMap<>();

        for (Question question : article.getQuestions()) {
            answersIcons.put(question.getId(), new HashMap<>());

            question.getAnswers()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(answer -> {
                        Map<Integer, byte[]> answerIcons = SquareImageAdapter
                                .unpack(
                                        getImages(
                                                config.getImagesAnswersIconsLocation().getValue(), answer.getId())
                                                .orElseGet(HashMap::new)
                                );

                        answersIcons.get(question.getId()).put(answer.getId(), answerIcons);
                    });
        }

        return answersIcons;
    }
}
