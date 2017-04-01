package acropollis.municipali.omega.admin.rest_service.customer;

import acropollis.municipali.omega.admin.data.dto.customer.Customer;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.service.customer.AdminCustomerService;
import acropollis.municipali.omega.common.exceptions.EntityNotFoundException;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminCustomerModelRestServiceImpl implements AdminCustomerRestService {
    @Autowired
    private AdminCustomerService adminCustomerService;

    @Override
    public CustomerInfo getCustomer(CustomerInfo user, String login) {
        return adminCustomerService
                .getCustomer(login)
                .map(Customer::getCustomerInfo)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Override
    public Collection<CustomerInfo> getAllCustomers(CustomerInfo user) {
        return adminCustomerService
                .getAllCustomers()
                .stream()
                .map(Customer::getCustomerInfo)
                .collect(Collectors.toList());
    }

    @Override
    public void createCustomer(CustomerInfo user, Customer customer) {
        adminCustomerService.createCustomer(customer);
    }

    @Override
    public void updateCustomer(CustomerInfo user, Customer customer) {
        adminCustomerService.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(CustomerInfo user, String login) {
        adminCustomerService.deleteCustomer(login);
    }
}
