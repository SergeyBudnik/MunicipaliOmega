package acropollis.municipali.omega.user.data.dto.user;

import acropollis.municipali.omega.common.dto.user.UserGender;
import lombok.Data;

@Data
public class UserDetailsInfo {
    private String name;
    private UserGender gender;
    private String email;
    private Long dateOfBirth;
}
