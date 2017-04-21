package acropollis.municipali.omega.database.db.converters.user;

import acropollis.municipali.omega.common.dto.user.User;
import acropollis.municipali.omega.common.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.common.dto.user.UserId;
import acropollis.municipali.omega.database.db.model.user.UserIdModel;
import acropollis.municipali.omega.database.db.model.user.UserModel;

public class UserModelConverter {
    public static User convert(UserModel userModel) {
        User user = new User();

        user.setUserId(convert(userModel.getId()));
        user.setUserDetailsInfo(new UserDetailsInfo()); {
            user.getUserDetailsInfo().setName(userModel.getName());
            user.getUserDetailsInfo().setEmail(userModel.getEmail());
            user.getUserDetailsInfo().setUserGender(userModel.getGender());
            user.getUserDetailsInfo().setDateOfBirth(userModel.getDateOfBirth());
        }

        return user;
    }

    public static UserId convert(UserIdModel userIdModel) {
        UserId userId = new UserId();

        userId.setUserLoginType(userIdModel.getLoginType());
        userId.setUserId(userIdModel.getUserId());

        return userId;
    }
}
