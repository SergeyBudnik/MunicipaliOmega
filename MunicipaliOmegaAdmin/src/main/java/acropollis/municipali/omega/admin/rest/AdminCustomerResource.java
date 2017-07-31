package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.security.common.dto.MunicipaliUser;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Api(tags = "Customer", description = "PROTECTED")
public class AdminCustomerResource extends AdminResource {
    @GetMapping("/{login}")
    public MunicipaliUserInfo getCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable String login
    ) {
        return super.getCustomer(authToken, login);
    }

    @GetMapping("")
    public List<MunicipaliUserInfo> getAllCustomers(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return super.getAllCustomers(authToken);
    }

    @PostMapping("")
    public void createCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody MunicipaliUser customer
    ) {
        super.createCustomer(authToken, customer);
    }

    @PutMapping("")
    public void updateCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody MunicipaliUser customer
    ) {
        super.editCustomer(authToken, customer);
    }

    @DeleteMapping("/{login}")
    public void deleteCustomer(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable String login
    ) {
        super.deleteCustomer(authToken, login);
    }
}
