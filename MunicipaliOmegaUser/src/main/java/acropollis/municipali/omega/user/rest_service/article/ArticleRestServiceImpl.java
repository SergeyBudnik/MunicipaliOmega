package acropollis.municipali.omega.user.rest_service.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import acropollis.municipali.omega.user.cache.view.ArticleViewCache;
import acropollis.municipali.omega.common.dto.views.ArticleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArticleRestServiceImpl implements ArticleRestService {
    @Autowired
    private VisibleArticlesCache visibleArticlesCache;
    @Autowired
    private ArticleViewCache articleViewCache;

    @Override
    public Collection<Article> getAllArticles() {
        return visibleArticlesCache.getArticles();
    }

    @Override
    public void addArticleView(ArticleView articleView) {
        boolean isArticlePresent = visibleArticlesCache
                .getArticle(articleView.getArticleId())
                .isPresent();

        if (isArticlePresent) {
            articleViewCache.addArticleView(articleView);
        }
    }
}
