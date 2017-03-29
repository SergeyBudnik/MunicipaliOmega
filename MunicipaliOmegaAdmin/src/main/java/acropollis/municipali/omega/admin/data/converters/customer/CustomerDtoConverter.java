package acropollis.municipali.omega.admin.data.converters.customer;

import acropollis.municipali.omega.admin.data.dto.customer.Customer;
import acropollis.municipali.omega.database.db.model.customer.CustomerModel;

public class CustomerDtoConverter {
    public static CustomerModel convert(Customer customer) {
        CustomerModel customerModel = new CustomerModel();

        customerModel.setLogin(customer.getLogin());
        customerModel.setPassword(customer.getPassword());

        return customerModel;
    }
}
