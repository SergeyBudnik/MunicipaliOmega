package acropollis.municipali.omega.common.dto.user;

import lombok.Data;

@Data
public class UserDetailsInfo {
    private String name;
    private UserGender userGender;
    private String email;
    private Long dateOfBirth;
}
