package acropollis.municipali.omega.common.dto.user;

import lombok.Data;

@Data
public class UserId {
    private UserLoginType userLoginType;
    private String userId;
}
