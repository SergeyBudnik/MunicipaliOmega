package acropollis.municipali.omega.tests.steps.admin;

import acropollis.municipali.omega.admin.data.dto.customer.Customer;
import acropollis.municipali.omega.admin.service.customer.AdminCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminCustomerSteps {
    @Autowired
    private AdminCustomerService adminCustomerService;

    public void createCustomer(String login, String password) {
        Customer customer = new Customer(); {
            customer.setLogin(login);
            customer.setPassword(password);
        }

        adminCustomerService.createCustomer(customer);
    }
}
