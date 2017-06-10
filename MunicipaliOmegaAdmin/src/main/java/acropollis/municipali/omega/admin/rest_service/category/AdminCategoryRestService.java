package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;

import java.util.List;

public interface AdminCategoryRestService {
    List<Category> getAllCategories(CustomerInfo customerInfo);
    Category getCategory(CustomerInfo customerInfo, long id);
    byte [] getCategoryIcon(CustomerInfo user, long id, int size);
    long createCategory(CustomerInfo customerInfo, CategoryWithIcon categoryWithIcon);
    void updateCategory(CustomerInfo customerInfo, CategoryWithIcon categoryWithIcon);
    void deleteCategory(CustomerInfo customerInfo, long id);
}
