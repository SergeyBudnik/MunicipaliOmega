package acropollis.municipali.omega.database.db.converters.customer;

import acropollis.municipali.omega.common.dto.customer.Customer;
import acropollis.municipali.omega.database.db.model.customer.CustomerModel;

public class CustomerModelConverter {
    public static Customer convert(CustomerModel customerModel) {
        Customer customer = new Customer();

        customer.setLogin(customerModel.getLogin());
        customer.setPassword(customerModel.getPassword());

        return customer;
    }
}
