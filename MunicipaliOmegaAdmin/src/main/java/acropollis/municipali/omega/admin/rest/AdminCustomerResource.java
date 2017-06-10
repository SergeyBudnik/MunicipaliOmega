package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.common.dto.customer.Customer;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.customer.AdminCustomerRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/customer")
@Api(tags = "Customer", description = "PROTECTED")
public class AdminCustomerResource {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private AdminCustomerRestService adminCustomerRestService;

    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public CustomerInfo getCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable String login
    ) {
        return adminCustomerRestService.getCustomer(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                login
        );
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<CustomerInfo> getAllCustomers(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return adminCustomerRestService.getAllCustomers(adminAuthenticationService.getCustomerInfoOrThrow(authToken));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody Customer customer
    ) {
        adminCustomerRestService.createCustomer(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                customer
        );
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody Customer customer
    ) {
        adminCustomerRestService.updateCustomer(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                customer
        );
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.DELETE)
    public void deleteCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable String login
    ) {
        adminCustomerRestService.deleteCustomer(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                login
        );
    }
}
