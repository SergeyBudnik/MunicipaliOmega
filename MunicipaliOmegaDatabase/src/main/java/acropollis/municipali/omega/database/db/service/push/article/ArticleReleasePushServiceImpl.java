package acropollis.municipali.omega.database.db.service.push.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.database.db.converters.article.ArticleModelConverter;
import acropollis.municipali.omega.database.db.dao.ArticleDao;
import acropollis.municipali.omega.database.db.dao.ArticleToReleasePushRecordDao;
import acropollis.municipali.omega.database.db.model.push.ArticleToReleasePushRecordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleReleasePushServiceImpl implements ArticleReleasePushService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleToReleasePushRecordDao articleToReleasePushRecordDao;

    @Override
    public List<Article> getArticlesToRelease(long to) {
        List<Long> articlesIdsToRelease = articleToReleasePushRecordDao
                .findByReleaseDateLessThan(to)
                .stream()
                .map(ArticleToReleasePushRecordModel::getArticleId)
                .collect(Collectors.toList());

        List<Article> articlesToRelease = new ArrayList<>();

        for (Long articleId : articlesIdsToRelease) {
            Optional
                    .ofNullable(articleDao.findOne(articleId))
                    .filter(it -> !it.isDeleted())
                    .map(ArticleModelConverter::convert)
                    .ifPresent(articlesToRelease::add);
        }

        return articlesToRelease;
    }

    @Override
    public void delete(long id) {
        articleToReleasePushRecordDao.delete(id);
    }
}
