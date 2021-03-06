package acropollis.municipali.omega.admin.rest_service.user;

import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.converters.user.UserModelConverter;
import acropollis.municipali.omega.database.db.dao.UserDao;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminUserRestServiceModelImpl implements AdminUserRestService {
    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public User getUserDetails(MunicipaliUserInfo userInfo, String userAuthToken) {
        return Optional
                .ofNullable(userDao.findByAuthToken(userAuthToken))
                .map(UserModelConverter::convert)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }
}
