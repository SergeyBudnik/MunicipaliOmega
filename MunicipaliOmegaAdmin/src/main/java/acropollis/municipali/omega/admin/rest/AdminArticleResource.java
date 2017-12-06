package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.article.AddOrUpdateArticleRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.article.AdminArticleRestService;
import acropollis.municipali.omega.admin.utils.log.LogUtils;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static java.lang.String.format;

@RestController
@RequestMapping("/article")
@Api(tags = "Article", description = "PROTECTED")
public class AdminArticleResource extends AdminResource {
    private static final Logger log = LogUtils.getArticlesResourceLogger();

    @Autowired
    @Qualifier(Qualifiers.REQUEST_VALIDATION)
    private AdminArticleRestService adminArticleRestService;

    @GetMapping("")
    public Collection<Article> getAllArticles(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        long t1 = System.currentTimeMillis();

        MunicipaliUserInfo municipaliUserInfo = getUserInfo(authToken);

        long t2 = System.currentTimeMillis();

        Collection<Article> articles = adminArticleRestService.getAllArticles(
                municipaliUserInfo
        );

        long t3 = System.currentTimeMillis();

        log.info(format("Read all articles: [Auth: %d ms, Reading: %d ms]", t3 - t2, t2 - t1));

        return articles;
    }

    @GetMapping("/{id}")
    public Article getArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        long t1 = System.currentTimeMillis();

        MunicipaliUserInfo municipaliUserInfo = getUserInfo(authToken);

        long t2 = System.currentTimeMillis();

        Article article = adminArticleRestService.getArticle(
                municipaliUserInfo,
                id
        );

        long t3 = System.currentTimeMillis();

        log.info(format("Read article #%d: [Auth: %d ms, Reading: %d ms]", id, t3 - t2, t2 - t1));

        return article;
    }

    @PostMapping("")
    public long createArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody AddOrUpdateArticleRequest request
    ) {
        long t1 = System.currentTimeMillis();

        MunicipaliUserInfo municipaliUserInfo = getUserInfo(authToken);

        long t2 = System.currentTimeMillis();

        long id = adminArticleRestService.createArticle(
                municipaliUserInfo,
                request.getArticle().toDto()
        );

        long t3 = System.currentTimeMillis();

        log.info(format("Create article #%d: [Auth: %d ms, Create: %d ms]", id, t3 - t2, t2 - t1));

        return id;
    }

    @PutMapping("/{id}")
    public void updateArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @RequestBody AddOrUpdateArticleRequest request
    ) {
        long t1 = System.currentTimeMillis();

        MunicipaliUserInfo municipaliUserInfo = getUserInfo(authToken);

        long t2 = System.currentTimeMillis();

        adminArticleRestService.updateArticle(
                municipaliUserInfo,
                request.getArticle().toDto(id)
        );

        long t3 = System.currentTimeMillis();

        log.info(format("Update article #%d: [Auth: %d ms, Update: %d ms]", id, t3 - t2, t2 - t1));
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        long t1 = System.currentTimeMillis();

        MunicipaliUserInfo municipaliUserInfo = getUserInfo(authToken);

        long t2 = System.currentTimeMillis();

        adminArticleRestService.deleteArticle(
                municipaliUserInfo,
                id
        );

        long t3 = System.currentTimeMillis();

        log.info(format("Delete article #%d: [Auth: %d ms, Delete: %d ms]", id, t3 - t2, t2 - t1));
    }
}
