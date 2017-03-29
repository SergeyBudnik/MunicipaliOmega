package acropollis.municipali.omega.admin.data.dto.article;

import lombok.Data;

import java.util.List;

@Data
public class TranslatedArticle {
    private String title;
    private String text;
    private List<String> categories;
}
