package acropollis.municipali.omega.user.cache.user;

import acropollis.municipali.omega.user.data.dto.user.User;
import acropollis.municipali.omega.user.data.dto.user.UserId;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserUserCacheImpl implements UserUserCache {
    private Map<UserId, User> usersCache = new ConcurrentHashMap<>();

    @Override
    public Optional<User> getUser(UserId userId) {
        return Optional.ofNullable(usersCache.get(userId));
    }

    @Override
    public void addUser(User user) {
        usersCache.put(user.getUserId(), user);
    }

    @Override
    public void removeUser(UserId userId) {
        usersCache.remove(userId);
    }
}
