package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;

import java.util.List;

public interface AdminCategoryRestService {
    List<Category> getAllCategories();
    Category getCategory(long id);
    long createCategory(CategoryWithIcon categoryWithIcon);
    void updateCategory(CategoryWithIcon categoryWithIcon);
    void deleteCategory(long id);
}
