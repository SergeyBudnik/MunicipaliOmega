package acropollis.municipali.omega.database.db.converters.category;

import acropollis.municipali.omega.common.dto.category.Category;
import acropollis.municipali.omega.common.dto.category.TranslatedCategory;
import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.database.db.model.category.CategoryModel;

import java.util.HashMap;
import java.util.Map;

import static acropollis.municipali.omega.common.utils.common.EncodingUtils.fromBase64;

public class CategoryModelConverter {
    public static Category convert(CategoryModel categoryModel) {
        Category category = new Category();

        category.setId(categoryModel.getId());
        category.setTranslatedCategory(convertTranslations(categoryModel));

        return category;
    }

    private static Map<Language, TranslatedCategory> convertTranslations(CategoryModel categoryModel) {
        Map<Language, TranslatedCategory> res = new HashMap<>();

        categoryModel.getTranslatedCategories().forEach(translatedCategoryModel -> {
            TranslatedCategory translatedCategory = new TranslatedCategory();

            translatedCategory.setTitle(fromBase64(translatedCategoryModel.getTitle()));
            translatedCategory.setText(fromBase64(translatedCategoryModel.getText()));

            res.put(translatedCategoryModel.getLanguage(), translatedCategory);
        });

        return res;
    }
}
