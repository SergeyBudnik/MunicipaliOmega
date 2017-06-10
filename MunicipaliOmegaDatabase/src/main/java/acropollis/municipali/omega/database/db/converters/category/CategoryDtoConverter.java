package acropollis.municipali.omega.database.db.converters.category;

import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.database.db.model.category.CategoryModel;
import acropollis.municipali.omega.database.db.model.category.TranslatedCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryDtoConverter {
    public static CategoryModel convert(Category category) {
        CategoryModel categoryModel = new CategoryModel();

        categoryModel.setId(category.getId());
        categoryModel.setTranslatedCategories(convert(category, categoryModel));
        categoryModel.setClickable(category.isClickable());
        categoryModel.setCreationDate(category.getCreationDate());
        categoryModel.setLastUpdateDate(category.getLastUpdateDate());

        return categoryModel;
    }

    private static List<TranslatedCategoryModel> convert(Category category, CategoryModel categoryModel) {
        List<TranslatedCategoryModel> res = new ArrayList<>();

        category.getTranslatedCategory().forEach((language, translatedCategory) -> {
            TranslatedCategoryModel translatedCategoryModel = new TranslatedCategoryModel();

            translatedCategoryModel.setCategory(categoryModel);
            translatedCategoryModel.setLanguage(language);
            translatedCategoryModel.setTitle(translatedCategory.getTitle());
            translatedCategoryModel.setText(translatedCategory.getText());

            res.add(translatedCategoryModel);
        });

        return res;
    }
}
