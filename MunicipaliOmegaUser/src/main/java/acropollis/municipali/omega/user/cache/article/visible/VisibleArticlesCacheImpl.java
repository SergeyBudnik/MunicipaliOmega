package acropollis.municipali.omega.user.cache.article.visible;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.dto.article.ArticleWithIcon;
import acropollis.municipali.omega.common.dto.article.question.QuestionWithIcon;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class VisibleArticlesCacheImpl implements VisibleArticlesCache {
    private AtomicReference<Map<Long, Article>> articlesCache = new AtomicReference<>(new ConcurrentHashMap<>());
    private AtomicReference<Map<Long, Map<Integer, byte []>>> articlesIconsCache = new AtomicReference<>(new ConcurrentHashMap<>());
    private AtomicReference<Map<Long, Map<Integer, byte []>>> articlesImagesCache = new AtomicReference<>(new ConcurrentHashMap<>());
    private AtomicReference<Map<Long, Map<Long, Map<Long, Map<Integer, byte []>>>>> answersIconsCache = new AtomicReference<>(new HashMap<>());

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Optional<Article> getArticle(long articleId) {
        try {
            lock.readLock().lock();

            return Optional.ofNullable(articlesCache.get().get(articleId));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Collection<Article> getArticles() {
        try {
            lock.readLock().lock();

            return articlesCache.get().values();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<byte []> getArticleIcon(long articleId, int size) {
        try {
            lock.readLock().lock();

            return Optional.ofNullable(
                    articlesIconsCache
                            .get()
                            .getOrDefault(articleId, new HashMap<>())
                            .get(size)
            );
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<byte[]> getArticleImage(long articleId, int size) {
        try {
            lock.readLock().lock();

            return Optional.ofNullable(
                    articlesImagesCache
                            .get()
                            .getOrDefault(articleId, new HashMap<>())
                            .get(size)
            );
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<byte []> getAnswerIcon(long articleId, long questionId, long answerId, int size) {
        try {
            lock.readLock().lock();

            return Optional.ofNullable(
                    answersIconsCache
                            .get()
                            .getOrDefault(articleId, new HashMap<>())
                            .getOrDefault(questionId, new HashMap<>())
                            .getOrDefault(answerId, new HashMap<>())
                            .get(size)
            );
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void setArticles(Collection<ArticleWithIcon> articles) {
        Map<Long, Article> newArticleCache = new HashMap<>();
        Map<Long, Map<Integer, byte []>> newArticlesIconsCache = new HashMap<>();
        Map<Long, Map<Integer, byte []>> newArticlesImagesCache = new HashMap<>();
        Map<Long, Map<Long, Map<Long, Map<Integer, byte []>>>> newAnswersIconsCache = new HashMap<>();

        for (ArticleWithIcon article : articles) {
            newArticleCache.put(article.getId(), article.withoutIcon());
            newArticlesIconsCache.put(article.getId(), article.getIcon());
            newArticlesImagesCache.put(article.getId(), article.getImage());

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

        try {
            lock.writeLock().lock();

            articlesCache.set(newArticleCache);
            articlesIconsCache.set(newArticlesIconsCache);
            articlesImagesCache.set(newArticlesImagesCache);
            answersIconsCache.set(newAnswersIconsCache);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
