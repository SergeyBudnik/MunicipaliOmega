package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.database.db.converters.views.ArticleViewDtoConverter;
import acropollis.municipali.omega.database.db.dao.ArticleViewDao;
import acropollis.municipali.omega.database.db.model.views.ArticleViewModel;
import acropollis.municipali.omega.user.cache.view.ArticleViewCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserArticlesViewsPersistJob {
    @Autowired
    private ArticleViewCache articleViewCache;

    @Autowired
    private ArticleViewDao articleViewDao;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void persist() {
        Set<ArticleViewModel> articlesViews = articleViewCache
                .getArticleViews()
                .stream()
                .map(ArticleViewDtoConverter::convert)
                .collect(Collectors.toSet());

        articleViewDao.save(articlesViews);
    }
}
