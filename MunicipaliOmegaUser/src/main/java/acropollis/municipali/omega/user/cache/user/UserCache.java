package acropollis.municipali.omega.user.cache.user;

import acropollis.municipali.omega.user.data.dto.user.User;
import acropollis.municipali.omega.user.data.dto.user.UserId;

import java.util.Optional;

public interface UserCache {
    Optional<User> getUser(UserId userId);
    void addUser(User user);
    void removeUser(UserId userId);
}
