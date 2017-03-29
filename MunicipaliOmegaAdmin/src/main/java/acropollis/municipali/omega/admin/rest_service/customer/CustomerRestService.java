package acropollis.municipali.omega.admin.rest_service.customer;

import acropollis.municipali.omega.admin.data.dto.customer.Customer;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;

import java.util.Collection;

public interface CustomerRestService {
    CustomerInfo getCustomer(CustomerInfo customerInfo, String login);
    Collection<CustomerInfo> getAllCustomers(CustomerInfo customerInfo);
    void createCustomer(CustomerInfo customerInfo, Customer customer);
    void updateCustomer(CustomerInfo customerInfo, Customer customer);
    void deleteCustomer(CustomerInfo customerInfo, String login);
}
