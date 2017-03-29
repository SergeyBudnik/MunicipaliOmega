package acropollis.municipali.omega.user.data.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
    private UserId userId;
    private UserServiceInfo userServiceInfo;
    private UserDetailsInfo userDetailsInfo;
}
