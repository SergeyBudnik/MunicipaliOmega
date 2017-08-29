package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.user.rest_service.article.ArticleRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/article")
@Api(tags = "Articles", description = " ")
public class UserArticleResource {
    @Autowired
    private ArticleRestService articleRestService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Article> getAllArticles() {
        return articleRestService.getAllArticles();
    }
}
