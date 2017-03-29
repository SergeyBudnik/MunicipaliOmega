package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.dto.customer.Customer;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.customer.CustomerRestService;
import acropollis.municipali.omega.admin.service.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/customer")
@Api(tags = "Customer", description = "PROTECTED")
public class CustomerResource {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private CustomerRestService customerRestService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public CustomerInfo getCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable String login
    ) {
        return customerRestService.getCustomer(
                authenticationService.getCustomerInfoOrThrow(authToken),
                login
        );
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<CustomerInfo> getAllCustomers(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return customerRestService.getAllCustomers(authenticationService.getCustomerInfoOrThrow(authToken));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody Customer customer
    ) {
        customerRestService.createCustomer(
                null,//authenticationService.getCustomerInfoOrThrow(authToken),
                customer
        );
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody Customer customer
    ) {
        customerRestService.updateCustomer(
                authenticationService.getCustomerInfoOrThrow(authToken),
                customer
        );
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.DELETE)
    public void deleteCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable String login
    ) {
        customerRestService.deleteCustomer(
                authenticationService.getCustomerInfoOrThrow(authToken),
                login
        );
    }
}
