package acropollis.municipali.omega.database.db.dao;

import acropollis.municipali.omega.database.db.model.user.UserIdModel;
import acropollis.municipali.omega.database.db.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserModel, UserIdModel> {
    UserModel findByAuthToken(String authToken);
}
