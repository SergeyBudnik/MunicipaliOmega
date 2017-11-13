package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.rest_service.views.AdminViewsRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/views")
@Api(tags = "Views", description = "PROTECTED")
public class AdminViewsResource extends AdminResource {
    @Autowired
    private AdminViewsRestService adminViewsRestService;

    @GetMapping("/article/{articleId}")
    public long getArticleViewsAmount(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long articleId
    ) {
        return adminViewsRestService.getArticleViewsAmount(
                getUserInfo(authToken),
                articleId
        );
    }
}
