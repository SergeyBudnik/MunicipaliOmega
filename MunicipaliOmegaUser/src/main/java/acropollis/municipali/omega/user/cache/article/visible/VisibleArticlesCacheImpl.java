package acropollis.municipali.omega.user.cache.article.visible;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VisibleArticlesCacheImpl implements VisibleArticlesCache {
    private Map<Long, Article> articlesCache = new ConcurrentHashMap<>();
    private Map<Long, Map<Integer, byte []>> articlesIconsCache = new ConcurrentHashMap<>();
    private Map<Long, Map<Long, Map<Long, Map<Integer, byte []>>>> answersIconsCache = new HashMap<>();

    @Override
    public Optional<Article> getArticle(long articleId) {
        return Optional.ofNullable(articlesCache.get(articleId));
    }

    @Override
    public Collection<Article> getArticles() {
        return articlesCache.values();
    }

    @Override
    public Optional<byte []> getArticleIcon(long articleId, int size) {
        return Optional.ofNullable(
                articlesIconsCache
                        .getOrDefault(articleId, new HashMap<>())
                        .get(size)
        );
    }

    @Override
    public Optional<byte []> getAnswerIcon(long articleId, long questionId, long answerId, int size) {
        return Optional.ofNullable(
                answersIconsCache
                        .getOrDefault(articleId, new HashMap<>())
                        .getOrDefault(questionId, new HashMap<>())
                        .getOrDefault(answerId, new HashMap<>())
                        .get(size)
        );
    }

    @Override
    public void setArticles(Collection<ArticleWithIcon> articles) {
        Map<Long, Article> newArticleCache = new HashMap<>();
        Map<Long, Map<Integer, byte []>> newArticlesIconsCache = new HashMap<>();
        Map<Long, Map<Long, Map<Long, Map<Integer, byte []>>>> newAnswersIconsCache = new HashMap<>();

        for (ArticleWithIcon article : articles) {
            newArticleCache.put(article.getId(), article.withoutIcon());
            newArticlesIconsCache.put(article.getId(), article.getIcon());

            newAnswersIconsCache.put(article.getId(), new HashMap<>());

            for (QuestionWithIcon question : article.getQuestions()) {
                Map<Long, Map<Integer, byte []>> questionIconsCache = new HashMap<>();

                question
                        .getAnswers()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(answer -> answer.getIcon() != null)
                        .filter(answer -> !answer.getIcon().isEmpty())
                        .forEach(answer -> questionIconsCache.put(answer.getId(), answer.getIcon()));

                newAnswersIconsCache
                        .get(article.getId())
                        .put(question.getId(), questionIconsCache);
            }
        }

        articlesCache = newArticleCache;
        articlesIconsCache = newArticlesIconsCache;
        answersIconsCache = newAnswersIconsCache;
    }
}
