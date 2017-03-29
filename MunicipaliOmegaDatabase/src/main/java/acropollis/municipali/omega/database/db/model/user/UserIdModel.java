package acropollis.municipali.omega.database.db.model.user;

import acropollis.municipali.omega.common.dto.user.UserLoginType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Embeddable
public class UserIdModel implements Serializable {
    @Column(name = "LOGIN_TYPE")
    private UserLoginType loginType;
    @Column(name = "USER_ID")
    private String userId;
}
