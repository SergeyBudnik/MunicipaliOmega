package acropollis.municipali.omega.user.async.article;

import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.user.cache.article.all.AllArticlesCache;
import acropollis.municipali.omega.user.data.dto.article.Article;
import acropollis.municipali.omega.user.data.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.user.data.dto.article.question.Question;
import acropollis.municipali.omega.user.image.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;
import static acropollis.municipali.omega.user.data.converter.article.ArticleModelConverter.convert;

@Service
public class AllArticlesReloadJob {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AllArticlesCache allArticlesCache;
    @Autowired
    private ImageStorageService imageStorageService;

    private long lastReloadDate = -1;

    @Scheduled(fixedDelay = 5 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        long newLastReloadDate = new Date().getTime();

        List<ArticleModel> articlesModels = lastReloadDate == -1 ?
                articleDao.findAll() :
                articleDao.findByLastUpdateDateGreaterThan(lastReloadDate);

        for (ArticleModel articleModel : articlesModels) {
            if (articleModel.isDeleted()) {
                allArticlesCache.removeArticle(articleModel.getId());
            } else {
                Article article = convert(articleModel);

                ArticleWithIcon articleWithIcon = article.withIcon(
                        getArticleIcons(article),
                        getAnswersIcons(article)
                );

                allArticlesCache.addArticle(articleWithIcon);
            }
        }

        lastReloadDate = newLastReloadDate;
    }

    private Map<Integer, byte []> getArticleIcons(Article article) {
        return imageStorageService
                .getImages(config.getString("images.articles"), article.getId())
                .orElseGet(HashMap::new);
    }

    private Map<Long, Map<Long, Map<Integer, byte []>>> getAnswersIcons(Article article) {
        Map<Long, Map<Long, Map<Integer, byte []>>> answersIcons = new HashMap<>();

        for (Question question : article.getQuestions()) {
            answersIcons.put(question.getId(), new HashMap<>());

            question.getAnswers()
                    .stream()
                    .filter(answer -> answer != null)
                    .forEach(answer -> {
                            Map<Integer, byte[]> answerIcons = imageStorageService
                                    .getImages(config.getString("images.answers"), answer.getId())
                                    .orElseGet(HashMap::new);

                            answersIcons.get(question.getId()).put(answer.getId(), answerIcons);
                    });
        }

        return answersIcons;
    }
}
