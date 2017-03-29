package acropollis.municipali.omega.admin.data.dto.user;

import acropollis.municipali.omega.common.dto.user.UserLoginType;
import lombok.Data;

@Data
public class UserId {

    private UserLoginType userLoginType;
    private String userId;
}
