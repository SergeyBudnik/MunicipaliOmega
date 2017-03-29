package acropollis.municipali.omega.user.data.converter.user;

import acropollis.municipali.omega.database.db.model.user.UserIdModel;
import acropollis.municipali.omega.database.db.model.user.UserModel;
import acropollis.municipali.omega.user.data.dto.user.User;
import acropollis.municipali.omega.user.data.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.user.data.dto.user.UserId;
import acropollis.municipali.omega.user.data.dto.user.UserServiceInfo;

public class UserModelConverter {
    public static User convert(UserModel userModel) {
        User user = new User();

        user.setUserId(convert(userModel.getId()));
        user.setUserDetailsInfo(new UserDetailsInfo()); {
            user.getUserDetailsInfo().setName(userModel.getName());
            user.getUserDetailsInfo().setEmail(userModel.getEmail());
            user.getUserDetailsInfo().setGender(userModel.getGender());
            user.getUserDetailsInfo().setDateOfBirth(userModel.getDateOfBirth());
        }
        user.setUserServiceInfo(new UserServiceInfo()); {
            user.getUserServiceInfo().setGmsToken(userModel.getGmsToken());
        }

        return user;
    }

    public static UserId convert(UserIdModel userIdModel) {
        UserId userId = new UserId();

        userId.setLoginType(userIdModel.getLoginType());
        userId.setUserId(userIdModel.getUserId());

        return userId;
    }
}
