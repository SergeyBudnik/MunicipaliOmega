package acropollis.municipali.omega.database.db.converters.views;

import acropollis.municipali.omega.common.dto.views.ArticleView;
import acropollis.municipali.omega.database.db.model.views.ArticleViewModel;

public class ArticleViewDtoConverter {
    public static ArticleViewModel convert(ArticleView articleView) {
        ArticleViewModel articleViewModel = new ArticleViewModel();

        articleViewModel.setArticleId(articleView.getArticleId());
        articleViewModel.setUserId(articleView.getUserId());

        return articleViewModel;
    }
}
