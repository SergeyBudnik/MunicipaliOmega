package acropollis.municipali.omega.database.db.service.user;

import acropollis.municipali.omega.common.dto.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByAuthToken(String authToken);
}
