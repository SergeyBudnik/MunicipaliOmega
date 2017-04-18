package acropollis.municipali.omega.admin.rest_service.user;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.common.dto.user.UserId;

public interface AdminUserRestService {
    UserDetailsInfo getUserDetails(CustomerInfo customerInfo, UserId userId);
}
