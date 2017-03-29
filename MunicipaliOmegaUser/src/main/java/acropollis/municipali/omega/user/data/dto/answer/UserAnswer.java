package acropollis.municipali.omega.user.data.dto.answer;

import lombok.Data;

@Data
public class UserAnswer {
    private String userAuthToken;
    private long articleId;
    private long questionId;
    private long answerId;
}
