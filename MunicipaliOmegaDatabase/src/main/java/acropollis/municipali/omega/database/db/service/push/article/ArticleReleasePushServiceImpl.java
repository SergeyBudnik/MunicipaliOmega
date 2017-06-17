package acropollis.municipali.omega.database.db.service.push.article;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.database.db.dao.ArticleToReleasePushRecordDao;
import acropollis.municipali.omega.database.db.model.push.ArticleToReleasePushRecordModel;
import acropollis.municipali.omega.database.db.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleReleasePushServiceImpl implements ArticleReleasePushService {
    @Autowired
    private ArticleToReleasePushRecordDao articleToReleasePushRecordDao;
    @Autowired
    private ArticleService articleService;

    @Transactional(readOnly = true)
    @Override
    public List<Article> getArticlesToRelease(long to) {
        return articleToReleasePushRecordDao
                .findByReleaseDateLessThan(to)
                .stream()
                .map(ArticleToReleasePushRecordModel::getArticleId)
                .map(it -> articleService.get(it))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(long articleId) {
        articleToReleasePushRecordDao.deleteByArticleId(articleId);
    }
}
