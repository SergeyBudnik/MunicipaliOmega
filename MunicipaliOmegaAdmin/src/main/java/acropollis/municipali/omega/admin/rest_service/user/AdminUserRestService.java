package acropollis.municipali.omega.admin.rest_service.user;

import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;

public interface AdminUserRestService {
    User getUserDetails(MunicipaliUserInfo userInfo, String userAuthToken);
}
