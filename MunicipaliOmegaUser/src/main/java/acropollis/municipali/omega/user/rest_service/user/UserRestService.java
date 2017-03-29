package acropollis.municipali.omega.user.rest_service.user;

import acropollis.municipali.omega.user.data.dto.user.User;

public interface UserRestService {
    String authenticate(User user);
}
