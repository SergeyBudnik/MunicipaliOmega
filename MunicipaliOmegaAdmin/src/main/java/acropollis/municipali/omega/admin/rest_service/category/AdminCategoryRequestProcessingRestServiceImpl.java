package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static acropollis.municipali.omega.common.utils.common.ImageUtils.resizeImages;

@Qualifier(Qualifiers.REQUEST_PROCESSING)
@Service
public class AdminCategoryRequestProcessingRestServiceImpl implements AdminCategoryRestService {
    @Qualifier(Qualifiers.MODEL)
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
        processIcons(categoryWithIcon);

        return adminCategoryRestService.createCategory(customerInfo, categoryWithIcon);
    }

    @Override
    public void updateCategory(CustomerInfo customerInfo, CategoryWithIcon categoryWithIcon) {
        processIcons(categoryWithIcon);

        adminCategoryRestService.updateCategory(customerInfo, categoryWithIcon);
    }

    @Override
    public void deleteCategory(CustomerInfo customerInfo, long id) {
        adminCategoryRestService.deleteCategory(customerInfo, id);
    }

    private void processIcons(CategoryWithIcon categoryWithIcon) {
        categoryWithIcon.setIcon(
                resizeImages(
                        categoryWithIcon.getIcon().get(-1),
                        100, 200, 300, 400, 500
                )
        );
    }
}
