package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.request.category.AddOrUpdateCategoryRequest;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.category.AdminCategoryRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import acropollis.municipali.omega.common.dto.category.Category;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Api(tags = "Category", description = "PROTECTED")
public class AdminCategoriesResource {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @Qualifier(Qualifiers.REQUEST_VALIDATION)
    @Autowired
    private AdminCategoryRestService adminCategoryRestService;

    @GetMapping("")
    public List<Category> getAllCategories(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return adminCategoryRestService.getAllCategories(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken)
        );
    }

    @GetMapping("/{id}")
    public Category getCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return adminCategoryRestService.getCategory(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }

    @GetMapping(
            value = "/{id}/icon/{size}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte [] getCategoryIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @PathVariable int size
    ) {
        return adminCategoryRestService.getCategoryIcon(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id,
                size
        );
    }

    @PostMapping("")
    public long createCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody AddOrUpdateCategoryRequest request
    ) {
        return adminCategoryRestService.createCategory(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
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
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                request.toCategory(id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        adminCategoryRestService.deleteCategory(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                id
        );
    }
}
