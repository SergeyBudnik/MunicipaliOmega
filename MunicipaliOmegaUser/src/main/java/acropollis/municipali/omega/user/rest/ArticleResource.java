package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.user.data.dto.article.Article;
import acropollis.municipali.omega.user.rest_service.article.ArticleRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/article")
@Api(tags = "Articles", description = " ")
public class ArticleResource {
    @Autowired
    private ArticleRestService articleRestService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Article> getAllArticles() {
        return articleRestService.getAllArticles();
    }

    @RequestMapping(
            value = "/{id}/icon/{size}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] getArticleIcon(@PathVariable long id, @PathVariable int size) {
        return articleRestService.getArticleIcon(id, size);
    }

    @RequestMapping(
            value = "/{articleId}/question/{questionId}/answer/{answerId}/icon/{size}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] getAnswerIcon(
            @PathVariable long articleId,
            @PathVariable long questionId,
            @PathVariable long answerId,
            @PathVariable int size
    ) {
        return articleRestService.getAnswerIcon(articleId, questionId, answerId, size);
    }
}
