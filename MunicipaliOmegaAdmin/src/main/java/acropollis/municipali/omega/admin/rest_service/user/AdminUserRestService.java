package acropollis.municipali.omega.admin.rest_service.user;

import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.user.User;

public interface AdminUserRestService {
    User getUserDetails(CustomerInfo customerInfo, String userAuthToken);
}
