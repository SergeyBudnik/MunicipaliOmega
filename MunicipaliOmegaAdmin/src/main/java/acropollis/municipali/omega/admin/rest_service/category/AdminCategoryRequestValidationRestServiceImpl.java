package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Qualifier(Qualifiers.REQUEST_VALIDATION)
@Service
public class AdminCategoryRequestValidationRestServiceImpl implements AdminCategoryRestService {
    @Qualifier(Qualifiers.REQUEST_PROCESSING)
    @Autowired
    private AdminCategoryRestService adminCategoryRestService;

    @Override
    public List<Category> getAllCategories(CustomerInfo customerInfo) {
        return adminCategoryRestService.getAllCategories(customerInfo);
    }

    @Override
    public Category getCategory(CustomerInfo customerInfo, long id) {
        return adminCategoryRestService.getCategory(customerInfo, id);
    }

    @Override
    public byte [] getCategoryIcon(CustomerInfo user, long id, int size) {
        return adminCategoryRestService.getCategoryIcon(user, id, size);
    }

    @Override
    public long createCategory(CustomerInfo customerInfo, CategoryWithIcon categoryWithIcon) {
        return adminCategoryRestService.createCategory(customerInfo, categoryWithIcon);
    }

    @Override
    public void updateCategory(CustomerInfo customerInfo, CategoryWithIcon categoryWithIcon) {
        adminCategoryRestService.updateCategory(customerInfo, categoryWithIcon);
    }

    @Override
    public void deleteCategory(CustomerInfo customerInfo, long id) {
        adminCategoryRestService.deleteCategory(customerInfo, id);
    }
}
