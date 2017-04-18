package acropollis.municipali.omega.common.dto.answer;

import acropollis.municipali.omega.common.dto.user.User;
import lombok.Data;

@Data
public class UserAnswer {
    private User user;
    private long articleId;
    private long questionId;
    private long answerId;
}
