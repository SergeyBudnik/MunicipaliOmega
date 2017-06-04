package acropollis.municipali.omega.admin.data.request.category;

import acropollis.municipali.omega.common.dto.category.CategoryWithIcon;
import acropollis.municipali.omega.common.dto.category.TranslatedCategory;
import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.util.Map;

@Data
public class AddOrUpdateCategoryRequest {
    private Map<Language, TranslatedCategory> translatedCategory;
    private Map<Integer, byte []> icon;

    public CategoryWithIcon toCategory() {
        return toCategory(null);
    }

    public CategoryWithIcon toCategory(Long id) {
        CategoryWithIcon categoryWithIcon = new CategoryWithIcon();

        categoryWithIcon.setId(id);
        categoryWithIcon.setTranslatedCategory(translatedCategory);
        categoryWithIcon.setIcon(icon);

        return categoryWithIcon;
    }
}
