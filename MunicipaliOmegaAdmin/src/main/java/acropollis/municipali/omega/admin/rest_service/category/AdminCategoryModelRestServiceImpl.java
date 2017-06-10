package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
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
        return new byte[0];
    }

    @Transactional
    @Override
    public long createCategory(CustomerInfo customerInfo, CategoryWithIcon categoryWithIcon) {
        CategoryModel categoryModel = categoryDao.save(CategoryDtoConverter.convert(categoryWithIcon.withoutIcon()));

        return categoryModel.getId();
    }

    @Transactional
    @Override
    public void updateCategory(CustomerInfo customerInfo, CategoryWithIcon category) {
        Optional
                .ofNullable(categoryDao.findOneByIdAndIsDeleted(category.getId(), false))
                .orElseThrow(() -> new HttpEntityNotFoundException(""));

        categoryDao.save(CategoryDtoConverter.convert(category.withoutIcon()));
    }

    @Transactional
    @Override
    public void deleteCategory(CustomerInfo customerInfo, long id) {
        CategoryModel categoryModel = categoryDao.findOneByIdAndIsDeleted(id, false);

        if (categoryModel == null) {
            throw new HttpEntityNotFoundException("");
        }

        //clearIcons(articleModel);

        categoryModel.setDeleted(true);
        categoryModel.getTranslatedCategories().clear();
    }
}
