package acropollis.municipali.omega.user.rest_service.user;

import acropollis.municipali.omega.common.dto.user.UserLoginType;
import acropollis.municipali.omega.database.db.dao.UserDao;
import acropollis.municipali.omega.database.db.model.user.UserIdModel;
import acropollis.municipali.omega.database.db.model.user.UserModel;
import acropollis.municipali.omega.user.data.converter.user.UserDtoConverter;
import acropollis.municipali.omega.user.data.dto.user.User;
import acropollis.municipali.omega.user.data.dto.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Random;

@Service
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    private UserDao userDao;

    private Random random = new Random();

    @Transactional(readOnly = false)
    @Override
    public String authenticate(User user) {
        if (user.getUserId().getLoginType() == UserLoginType.NONE) {
            user.getUserId().setUserId(user.getUserServiceInfo().getGmsToken());
        }

        UserIdModel userIdModel = UserDtoConverter.convert(user.getUserId());

        UserModel userModel = userDao.findOne(userIdModel);

        String authToken = userModel == null ?
                generateNotExistingAuthToken() :
                userModel.getAuthToken();

        userDao.save(UserDtoConverter.convert(user, authToken));

        return authToken;
    }

    private String generateNotExistingAuthToken() {
        while (true) {
            String authToken = generateAuthToken();

            if (userDao.findByAuthToken(authToken) == null) {
                return authToken;
            }
        }
    }

    private String generateAuthToken() {
        return new BigInteger(130, random).toString(32);
    }
}
