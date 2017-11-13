package acropollis.municipali.omega.user.cache.view;

import acropollis.municipali.omega.common.dto.views.ArticleView;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class ArticleViewCacheImpl implements ArticleViewCache {
    private ConcurrentHashMap<ArticleView, ArticleView> cache = new ConcurrentHashMap<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void addArticleView(ArticleView articleView) {
        try {
            lock.writeLock().lock();

            cache.put(articleView, articleView);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Set<ArticleView> getArticleViews() {
        try {
            lock.writeLock().lock();

            Set<ArticleView> views = new HashSet<>(cache.keySet());

            cache.clear();

            return views;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
