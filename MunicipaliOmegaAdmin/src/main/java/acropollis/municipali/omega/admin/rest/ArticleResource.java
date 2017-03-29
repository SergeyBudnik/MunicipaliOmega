package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.article.AddOrUpdateArticleRequest;
import acropollis.municipali.omega.admin.data.dto.article.Article;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.article.ArticleRestService;
import acropollis.municipali.omega.admin.service.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/article")
@Api(tags = "Article", description = "PROTECTED")
public class ArticleResource {
    @Autowired
    @Qualifier(Qualifiers.REQUEST_VALIDATION)
    private ArticleRestService articleRestService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Article> getAllArticles(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return articleRestService.getAllArticles(authenticationService.getCustomerInfoOrThrow(authToken));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Article getArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return articleRestService.getArticle(
                authenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }

    @RequestMapping(
            value = "/{id}/icon/{size}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] getArticleIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @PathVariable int size
    ) {
        return articleRestService.getArticleIcon(
                authenticationService.getCustomerInfoOrThrow(authToken),
                id,
                size
        );
    }

    @RequestMapping(
            value = "/{articleId}/question/{questionId}/answer/{answerId}/icon/{size}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] getAnswerIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long articleId,
            @PathVariable long questionId,
            @PathVariable long answerId,
            @PathVariable int size
    ) {
        return articleRestService.getAnswerIcon(
                authenticationService.getCustomerInfoOrThrow(authToken),
                articleId,
                questionId,
                answerId,
                size
        );
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public long createArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody AddOrUpdateArticleRequest request
    ) {
        return articleRestService.createArticle(
                authenticationService.getCustomerInfoOrThrow(authToken),
                request.getArticle().toDto()
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @RequestBody AddOrUpdateArticleRequest request
    ) {
        articleRestService.updateArticle(
                authenticationService.getCustomerInfoOrThrow(authToken),
                request.getArticle().toDto(id)
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        articleRestService.deleteArticle(
                authenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }
}
