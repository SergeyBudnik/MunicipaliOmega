package acropollis.municipali.omega.user.cache.article.all;

import acropollis.municipali.omega.user.data.dto.article.ArticleWithIcon;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AllArticlesCacheImpl implements AllArticlesCache {
    private Map<Long, ArticleWithIcon> cache = new ConcurrentHashMap<>();

    @Override
    public Collection<ArticleWithIcon> getAllArticles() {
        return cache.values();
    }

    @Override
    public void addArticle(ArticleWithIcon article) {
        cache.put(article.getId(), article);
    }

    @Override
    public void removeArticle(long id) {
        cache.remove(id);
    }
}
