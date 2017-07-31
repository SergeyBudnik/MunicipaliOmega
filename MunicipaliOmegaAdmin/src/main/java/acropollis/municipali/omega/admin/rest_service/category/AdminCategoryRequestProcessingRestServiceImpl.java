package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
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
        processIcons(categoryWithIcon);

        return adminCategoryRestService.createCategory(userInfo, categoryWithIcon);
    }

    @Override
    public void updateCategory(MunicipaliUserInfo userInfo, CategoryWithIcon categoryWithIcon) {
        processIcons(categoryWithIcon);

        adminCategoryRestService.updateCategory(userInfo, categoryWithIcon);
    }

    @Override
    public void deleteCategory(MunicipaliUserInfo userInfo, long id) {
        adminCategoryRestService.deleteCategory(userInfo, id);
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
