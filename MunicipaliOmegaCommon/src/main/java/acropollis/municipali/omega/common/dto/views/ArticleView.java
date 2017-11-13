package acropollis.municipali.omega.common.dto.views;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticleView {
    private String userId;
    private long articleId;
}
