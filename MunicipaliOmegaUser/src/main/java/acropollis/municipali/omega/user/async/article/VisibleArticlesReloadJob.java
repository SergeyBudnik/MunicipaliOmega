package acropollis.municipali.omega.user.async.article;

import acropollis.municipali.omega.user.cache.article.all.AllArticlesCache;
import acropollis.municipali.omega.user.cache.article.visible.VisibleArticlesCache;
import acropollis.municipali.omega.user.data.dto.article.ArticleWithIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class VisibleArticlesReloadJob {
    @Autowired
    private VisibleArticlesCache visibleArticlesCache;
    @Autowired
    private AllArticlesCache allArticlesCache;

    @Scheduled(fixedRate = 5 * 1000)
    public void reload() {
        long currentDate = new Date().getTime();

        Collection<ArticleWithIcon> visibleArticles = new ArrayList<>();

        for (ArticleWithIcon article : allArticlesCache.getAllArticles()) {
            boolean isVisible = article.getReleaseDate() <= currentDate;

            if (isVisible) {
                visibleArticles.add(article);
            }
        }

        visibleArticlesCache.setArticles(visibleArticles);
    }
}
