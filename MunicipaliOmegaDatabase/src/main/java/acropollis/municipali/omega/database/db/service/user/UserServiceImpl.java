package acropollis.municipali.omega.database.db.service.user;

import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.database.db.converters.user.UserModelConverter;
import acropollis.municipali.omega.database.db.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> getByAuthToken(String authToken) {
        return Optional
                .ofNullable(userDao.findByAuthToken(authToken))
                .map(UserModelConverter::convert);
    }
}
