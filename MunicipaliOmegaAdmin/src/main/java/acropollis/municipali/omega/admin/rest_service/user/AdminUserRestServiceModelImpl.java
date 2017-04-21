package acropollis.municipali.omega.admin.rest_service.user;

import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.database.db.converters.user.UserDtoConverter;
import acropollis.municipali.omega.database.db.converters.user.UserModelConverter;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.common.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.common.dto.user.UserId;
import acropollis.municipali.omega.database.db.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminUserRestServiceModelImpl implements AdminUserRestService {
    @Autowired
    UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetailsInfo getUserDetails(CustomerInfo customerInfo, UserId userId) {
        return Optional
                .ofNullable(userDao.findOne(UserDtoConverter.convert(userId)))
                .map(UserModelConverter::convert)
                .map(User::getUserDetailsInfo)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }
}
