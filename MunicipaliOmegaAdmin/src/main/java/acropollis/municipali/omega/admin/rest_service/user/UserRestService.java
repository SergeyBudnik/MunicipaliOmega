package acropollis.municipali.omega.admin.rest_service.user;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.admin.data.dto.user.UserId;

public interface UserRestService {
    UserDetailsInfo getUserDetails(CustomerInfo customerInfo, UserId userId);
}
