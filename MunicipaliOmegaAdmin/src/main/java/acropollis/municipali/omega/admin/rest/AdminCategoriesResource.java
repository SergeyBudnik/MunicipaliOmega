package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.rest_service.category.AdminCategoryRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Api(tags = "Category", description = "PROTECTED")
public class AdminCategoriesResource {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @Autowired
    private AdminCategoryRestService adminCategoryRestService;

    @GetMapping("")
    public List<Category> getAllCategories(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return null;
    }

    @GetMapping("/{id}")
    public Category getCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {
        return null;
    }

    @PostMapping("")
    public long createCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody CategoryWithIcon categoryWithIcon
    ) {
        return 0;
    }

    @PutMapping("/{id}")
    public long updateCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id,
            @RequestBody CategoryWithIcon categoryWithIcon
    ) {
        return 0;
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable long id
    ) {

    }
}
