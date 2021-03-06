package acropollis.municipali.omega.common.dto.article;

import lombok.Data;

import java.util.List;

@Data
public class TranslatedArticle {
    private String title;
    private String description;
    private String text;
    private List<String> categories;
}
