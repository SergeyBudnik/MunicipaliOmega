package acropollis.municipali.omega.common.dto.customer;

import lombok.Data;

import java.beans.Transient;

@Data
public class Customer {
    private String login;
    private String password;

    @Transient
    public CustomerInfo getCustomerInfo() {
        CustomerInfo customerInfo = new CustomerInfo();

        customerInfo.setLogin(login);

        return customerInfo;
    }
}
