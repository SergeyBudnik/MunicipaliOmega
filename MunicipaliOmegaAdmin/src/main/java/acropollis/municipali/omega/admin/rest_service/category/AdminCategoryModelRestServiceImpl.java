package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.common.utils.storage.EntityImageStorageUtils;
import acropollis.municipali.omega.common.utils.storage.SquareImageAdapter;
import acropollis.municipali.omega.database.db.converters.category.CategoryDtoConverter;
import acropollis.municipali.omega.database.db.converters.category.CategoryModelConverter;
import acropollis.municipali.omega.database.db.dao.CategoryDao;
import acropollis.municipali.omega.database.db.model.category.CategoryModel;
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

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories(CustomerInfo customerInfo) {
        return categoryDao
                .findAll()
                .stream()
                .map(CategoryModelConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategory(CustomerInfo customerInfo, long id) {
        return Optional
                .ofNullable(categoryDao.findOne(id))
                .map(CategoryModelConverter::convert)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional(readOnly = true)
    @Override
    public byte [] getCategoryIcon(CustomerInfo user, long id, int size) {
        return EntityImageStorageUtils.getImage(
                config.getImagesCategoriesIconsLocation().getValue(),
                id,
                size,
                size
        ).orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional
    @Override
    public long createCategory(CustomerInfo customerInfo, CategoryWithIcon categoryWithIcon) {
        CategoryModel categoryModel = categoryDao.save(CategoryDtoConverter.convert(categoryWithIcon.withoutIcon()));

        saveIcons(categoryModel, categoryWithIcon);

        return categoryModel.getId();
    }

    @Transactional
    @Override
    public void updateCategory(CustomerInfo customerInfo, CategoryWithIcon category) {
        CategoryModel oldCategoryModel = Optional
                .ofNullable(categoryDao.findOneByIdAndIsDeleted(category.getId(), false))
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        CategoryModel newCategoryModel = categoryDao.save(CategoryDtoConverter.convert(category.withoutIcon()));

        clearIcons(oldCategoryModel.getId());
        saveIcons(newCategoryModel, category);
    }

    @Transactional
    @Override
    public void deleteCategory(CustomerInfo customerInfo, long id) {
        CategoryModel categoryModel = categoryDao.findOneByIdAndIsDeleted(id, false);

        if (categoryModel == null) {
            throw new HttpEntityNotFoundException("");
        }

        clearIcons(categoryModel.getId());

        categoryModel.setDeleted(true);
        categoryModel.getTranslatedCategories().clear();
    }

    private void saveIcons(CategoryModel categoryModel, CategoryWithIcon categoryWithIcon) {
        if (!categoryWithIcon.getIcon().isEmpty()) {
            EntityImageStorageUtils.saveImages(
                    config.getImagesArticlesIconsLocation().getValue(),
                    categoryModel.getId(),
                    SquareImageAdapter.pack(categoryWithIcon.getIcon())
            );
        }
    }

    private void clearIcons(long id) {
        EntityImageStorageUtils.removeImages(
                config.getImagesArticlesIconsLocation().getValue(),
                id
        );
    }
}
