package acropollis.municipali.omega.admin.service.customer;

import acropollis.municipali.omega.admin.data.dto.customer.Customer;

import java.util.Collection;
import java.util.Optional;

public interface AdminCustomerService {
    Optional<Customer> getCustomer(String login);
    Optional<Customer> getCustomer(String login, String password);
    Collection<Customer> getAllCustomers();
    void createCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(String login);
}
