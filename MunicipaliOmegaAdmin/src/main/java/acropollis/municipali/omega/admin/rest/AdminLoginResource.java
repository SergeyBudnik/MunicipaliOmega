package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerCredentials;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerToken;
import acropollis.municipali.omega.common.exceptions.HttpCredentialsViolationException;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(tags = "Login", description = "PROTECTED")
public class AdminLoginResource {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public CustomerToken login(@RequestBody CustomerCredentials customerCredentials) {
        return adminAuthenticationService
                .login(customerCredentials)
                .orElseThrow(() -> new HttpCredentialsViolationException(""));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public void logoff(@RequestBody String authToken) {
        adminAuthenticationService.logoff(authToken);
    }
}
