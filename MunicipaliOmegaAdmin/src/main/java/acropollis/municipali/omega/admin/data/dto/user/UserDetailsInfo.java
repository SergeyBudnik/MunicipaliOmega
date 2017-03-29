package acropollis.municipali.omega.admin.data.dto.user;

import acropollis.municipali.omega.common.dto.user.UserGender;
import lombok.Data;

@Data
public class UserDetailsInfo {

    private String name;
    private UserGender userGender;
    private String email;
    private Long dateOfBirth;
}
