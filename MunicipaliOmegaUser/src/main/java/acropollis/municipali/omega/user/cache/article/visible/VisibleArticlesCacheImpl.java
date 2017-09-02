package acropollis.municipali.omega.user.cache.article.visible;

import acropollis.municipali.omega.common.dto.article.Article;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class VisibleArticlesCacheImpl implements VisibleArticlesCache {
    private AtomicReference<Map<Long, Article>> articlesCache = new AtomicReference<>(new ConcurrentHashMap<>());

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
    public void setArticles(Collection<Article> articles) {
        Map<Long, Article> newArticleCache = new HashMap<>();

        for (Article article : articles) {
            newArticleCache.put(article.getId(), article);
        }

        try {
            lock.writeLock().lock();

            articlesCache.set(newArticleCache);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
