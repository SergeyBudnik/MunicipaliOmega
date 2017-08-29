package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.category.AddOrUpdateCategoryRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.category.AdminCategoryRestService;
import acropollis.municipali.omega.common.dto.category.Category;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Api(tags = "Category", description = "PROTECTED")
public class AdminCategoriesResource extends AdminResource {
    @Qualifier(Qualifiers.REQUEST_VALIDATION)
    @Autowired
    private AdminCategoryRestService adminCategoryRestService;

    @GetMapping("")
    public List<Category> getAllCategories(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return adminCategoryRestService.getAllCategories(
                getUserInfo(authToken)
        );
    }

    @GetMapping("/{id}")
    public Category getCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return adminCategoryRestService.getCategory(
                getUserInfo(authToken),
                id
        );
    }

    @PostMapping("")
    public long createCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody AddOrUpdateCategoryRequest request
    ) {
        return adminCategoryRestService.createCategory(
                getUserInfo(authToken),
                request.toCategory()
        );
    }

    @PutMapping("/{id}")
    public void updateCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @RequestBody AddOrUpdateCategoryRequest request
    ) {
        adminCategoryRestService.updateCategory(
                getUserInfo(authToken),
                request.toCategory(id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        adminCategoryRestService.deleteCategory(
                getUserInfo(authToken),
                id
        );
    }
}
