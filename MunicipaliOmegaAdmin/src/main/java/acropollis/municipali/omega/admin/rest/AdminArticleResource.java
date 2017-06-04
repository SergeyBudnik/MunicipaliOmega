package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.article.AddOrUpdateArticleRequest;
import acropollis.municipali.omega.admin.rest_service.article.AdminArticleRestService;
import acropollis.municipali.omega.common.dto.article.Article;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
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
public class AdminArticleResource {
    @Autowired
    @Qualifier(Qualifiers.REQUEST_VALIDATION)
    private AdminArticleRestService adminArticleRestService;

    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @GetMapping("")
    public Collection<Article> getAllArticles(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return adminArticleRestService.getAllArticles(adminAuthenticationService.getCustomerInfoOrThrow(authToken));
    }

    @GetMapping("/{id}")
    public Article getArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return adminArticleRestService.getArticle(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }

    @GetMapping(
            value = "/{id}/icon/{size}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] getArticleIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @PathVariable int size
    ) {
        return adminArticleRestService.getArticleIcon(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id,
                size
        );
    }

    @GetMapping(
            value = "/{articleId}/question/{questionId}/answer/{answerId}/icon/{size}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] getAnswerIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long articleId,
            @PathVariable long questionId,
            @PathVariable long answerId,
            @PathVariable int size
    ) {
        return adminArticleRestService.getAnswerIcon(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                articleId,
                questionId,
                answerId,
                size
        );
    }

    @PostMapping("")
    public long createArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody AddOrUpdateArticleRequest request
    ) {
        return adminArticleRestService.createArticle(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                request.getArticle().toDto()
        );
    }

    @PutMapping("/{id}")
    public void updateArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @RequestBody AddOrUpdateArticleRequest request
    ) {
        adminArticleRestService.updateArticle(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                request.getArticle().toDto(id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        adminArticleRestService.deleteArticle(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }
}
