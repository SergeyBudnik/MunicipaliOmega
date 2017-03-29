package acropollis.municipali.omega.user.data.dto.user;

import acropollis.municipali.omega.common.dto.user.UserLoginType;
import lombok.Data;

@Data
public class UserId {
    private UserLoginType loginType;
    private String userId;
}
