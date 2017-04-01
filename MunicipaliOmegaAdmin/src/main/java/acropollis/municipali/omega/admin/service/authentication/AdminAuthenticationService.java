package acropollis.municipali.omega.admin.service.authentication;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerCredentials;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerToken;

import java.util.Optional;

public interface AdminAuthenticationService {
    Optional<CustomerToken> login(CustomerCredentials customerCredentials);
    void logoff(String token);
    Optional<CustomerInfo> getCustomerInfo(String token);
    CustomerInfo getCustomerInfoOrThrow(String token);
}
