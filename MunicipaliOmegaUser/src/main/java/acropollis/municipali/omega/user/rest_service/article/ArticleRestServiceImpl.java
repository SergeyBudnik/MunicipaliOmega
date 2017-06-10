package acropollis.municipali.omega.user.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArticleRestServiceImpl implements ArticleRestService {
    @Autowired
    private VisibleArticlesCache visibleArticlesCache;

    @Override
    public Collection<Article> getAllArticles() {
        return visibleArticlesCache.getArticles();
    }

    @Override
    public byte [] getArticleIcon(long articleId, int size) {
        return visibleArticlesCache
                .getArticleIcon(articleId, size)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Override
    public byte [] getAnswerIcon(long articleId, long questionId, long answerId, int size) {
        return visibleArticlesCache
                .getAnswerIcon(articleId, questionId, answerId, size)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }
}
