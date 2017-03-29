package acropollis.municipali.omega.user_notification.converter.article;

import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.database.db.model.article.ArticleModel;
import acropollis.municipali.omega.database.db.model.article.TranslatedArticleModel;
import acropollis.municipali.omega.user_notification.dto.article.Article;
import acropollis.municipali.omega.user_notification.dto.article.TranslatedArticle;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleModelConverter {
    public static Article convert(ArticleModel articleModel) {
        Article article = new Article();

        article.setId(articleModel.getId());
        article.setTranslatedArticle(convert(articleModel.getTranslatedArticles()));

        return article;
    }

    private static Map<Language, TranslatedArticle> convert(Collection<TranslatedArticleModel> translatedArticleModels) {
        return translatedArticleModels
                .stream()
                .collect(Collectors.toMap(TranslatedArticleModel::getLanguage, ArticleModelConverter::convert));
    }

    private static TranslatedArticle convert(TranslatedArticleModel translatedArticleModel) {
        TranslatedArticle translatedArticle = new TranslatedArticle();

        translatedArticle.setTitle(translatedArticleModel.getTitle());
        translatedArticle.setText(translatedArticleModel.getText());

        return translatedArticle;
    }
}
