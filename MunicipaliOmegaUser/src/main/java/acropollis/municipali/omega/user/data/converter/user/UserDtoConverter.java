package acropollis.municipali.omega.user.data.converter.user;

import acropollis.municipali.omega.database.db.model.user.UserIdModel;
import acropollis.municipali.omega.database.db.model.user.UserModel;
import acropollis.municipali.omega.user.data.dto.user.User;
import acropollis.municipali.omega.user.data.dto.user.UserId;

import static acropollis.municipali.omega.common.utils.common.EncodingUtils.toBase64;

public class UserDtoConverter {
    public static UserModel convert(User user, String authToken) {
        UserModel userModel = new UserModel();

        userModel.setId(convert(user.getUserId()));
        userModel.setAuthToken(authToken);

        userModel.setGmsToken(user.getUserServiceInfo().getGmsToken());

        userModel.setName(toBase64(user.getUserDetailsInfo().getName()));
        userModel.setEmail(user.getUserDetailsInfo().getEmail());
        userModel.setGender(user.getUserDetailsInfo().getGender());
        userModel.setDateOfBirth(user.getUserDetailsInfo().getDateOfBirth());

        return userModel;
    }

    public static UserIdModel convert(UserId userId) {
        UserIdModel userIdModel = new UserIdModel();

        userIdModel.setLoginType(userId.getLoginType());
        userIdModel.setUserId(userId.getUserId());

        return userIdModel;
    }
}
