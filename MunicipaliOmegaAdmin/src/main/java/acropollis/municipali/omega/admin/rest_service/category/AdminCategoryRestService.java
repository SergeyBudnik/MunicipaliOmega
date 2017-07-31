package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;

import java.util.List;

public interface AdminCategoryRestService {
    List<Category> getAllCategories(MunicipaliUserInfo userInfo);
    Category getCategory(MunicipaliUserInfo userInfo, long id);
    byte [] getCategoryIcon(MunicipaliUserInfo userInfo, long id, int size);
    long createCategory(MunicipaliUserInfo userInfo, CategoryWithIcon categoryWithIcon);
    void updateCategory(MunicipaliUserInfo userInfo, CategoryWithIcon categoryWithIcon);
    void deleteCategory(MunicipaliUserInfo userInfo, long id);
}
