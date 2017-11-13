package acropollis.municipali.omega.user.cache.view;

import acropollis.municipali.omega.common.dto.views.ArticleView;

import java.util.Set;

public interface ArticleViewCache {
    void addArticleView(ArticleView articleView);
    Set<ArticleView> getArticleViews();
}
