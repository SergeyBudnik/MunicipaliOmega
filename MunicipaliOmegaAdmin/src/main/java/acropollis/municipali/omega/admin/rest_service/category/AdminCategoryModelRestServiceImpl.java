package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.converters.category.CategoryDtoConverter;
import acropollis.municipali.omega.database.db.converters.category.CategoryModelConverter;
import acropollis.municipali.omega.database.db.dao.CategoryDao;
import acropollis.municipali.omega.database.db.model.category.CategoryModel;
import acropollis.municipali.omega.database.db.service.image.ImageService;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Qualifier(Qualifiers.MODEL)
@Service
public class AdminCategoryModelRestServiceImpl implements AdminCategoryRestService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ImageService imageService;

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories(MunicipaliUserInfo userInfo) {
        return categoryDao
                .findAll()
                .stream()
                .map(CategoryModelConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategory(MunicipaliUserInfo userInfo, long id) {
        return Optional
                .ofNullable(categoryDao.findOne(id))
                .map(CategoryModelConverter::convert)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional
    @Override
    public long createCategory(MunicipaliUserInfo userInfo, CategoryWithIcon categoryWithIcon) {
        CategoryModel categoryModel = categoryDao.save(CategoryDtoConverter.convert(categoryWithIcon.withoutIcon()));

        saveIcons(categoryModel, categoryWithIcon);

        return categoryModel.getId();
    }

    @Transactional
    @Override
    public void updateCategory(MunicipaliUserInfo userInfo, CategoryWithIcon category) {
        CategoryModel oldCategoryModel = Optional
                .ofNullable(categoryDao.findOneByIdAndIsDeleted(category.getId(), false))
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        CategoryModel newCategoryModel = categoryDao.save(CategoryDtoConverter.convert(category.withoutIcon()));

        clearIcons(oldCategoryModel.getId());
        saveIcons(newCategoryModel, category);
    }

    @Transactional
    @Override
    public void deleteCategory(MunicipaliUserInfo userInfo, long id) {
        CategoryModel categoryModel = categoryDao.findOneByIdAndIsDeleted(id, false);

        if (categoryModel == null) {
            throw new HttpEntityNotFoundException("");
        }

        clearIcons(categoryModel.getId());

        categoryModel.setDeleted(true);
        categoryModel.getTranslatedCategories().clear();
    }

    private void saveIcons(CategoryModel categoryModel, CategoryWithIcon categoryWithIcon) {
        categoryWithIcon.getIcon().forEach((size, icon) ->
                imageService.addImage(
                        config.getImagesCategoriesIconsLocation(),
                        String.format("%d", categoryModel.getId()),
                        String.format("%dx%d", size, size),
                        icon
                )
        );
    }

    private void clearIcons(long id) {
        imageService.removeAllImagesRemoveDirectory(
                config.getImagesCategoriesIconsLocation(),
                String.format("%d", id)
        );
    }
}
