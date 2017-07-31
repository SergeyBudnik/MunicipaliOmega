package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
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
    public List<Category> getAllCategories(MunicipaliUserInfo userInfo) {
        return adminCategoryRestService.getAllCategories(userInfo);
    }

    @Override
    public Category getCategory(MunicipaliUserInfo userInfo, long id) {
        return adminCategoryRestService.getCategory(userInfo, id);
    }

    @Override
    public byte [] getCategoryIcon(MunicipaliUserInfo userInfo, long id, int size) {
        return adminCategoryRestService.getCategoryIcon(userInfo, id, size);
    }

    @Override
    public long createCategory(MunicipaliUserInfo userInfo, CategoryWithIcon categoryWithIcon) {
        return adminCategoryRestService.createCategory(userInfo, categoryWithIcon);
    }

    @Override
    public void updateCategory(MunicipaliUserInfo userInfo, CategoryWithIcon categoryWithIcon) {
        adminCategoryRestService.updateCategory(userInfo, categoryWithIcon);
    }

    @Override
    public void deleteCategory(MunicipaliUserInfo userInfo, long id) {
        adminCategoryRestService.deleteCategory(userInfo, id);
    }
}
