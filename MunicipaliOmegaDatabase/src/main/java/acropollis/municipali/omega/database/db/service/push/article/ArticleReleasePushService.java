package acropollis.municipali.omega.database.db.service.push.article;

import acropollis.municipali.omega.common.dto.article.Article;

import java.util.List;

public interface ArticleReleasePushService {
    List<Article> getArticlesToRelease(long to);
    void delete(long id);
}
