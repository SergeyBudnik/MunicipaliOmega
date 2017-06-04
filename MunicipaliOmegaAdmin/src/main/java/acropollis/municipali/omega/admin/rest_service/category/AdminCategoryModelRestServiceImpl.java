package acropollis.municipali.omega.admin.rest_service.category;

import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.converters.category.CategoryModelConverter;
import acropollis.municipali.omega.database.db.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminCategoryModelRestServiceImpl implements AdminCategoryRestService {
    @Autowired
    private CategoryDao categoryDao;

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories() {
        return categoryDao
                .findAll()
                .stream()
                .map(CategoryModelConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategory(long id) {
        return Optional
                .ofNullable(categoryDao.findOne(id))
                .map(CategoryModelConverter::convert)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Transactional
    @Override
    public long createCategory(CategoryWithIcon categoryWithIcon) {
        return 0;
    }

    @Transactional
    @Override
    public void updateCategory(CategoryWithIcon categoryWithIcon) {

    }

    @Transactional
    @Override
    public void deleteCategory(long id) {

    }
}
