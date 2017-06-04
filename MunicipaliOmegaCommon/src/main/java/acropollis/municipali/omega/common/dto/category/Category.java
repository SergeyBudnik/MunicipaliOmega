package acropollis.municipali.omega.common.dto.category;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.Data;

import java.util.Map;

@Data
public class Category {
    private Long id;
    private Map<Language, TranslatedCategory> translatedCategory;
}
