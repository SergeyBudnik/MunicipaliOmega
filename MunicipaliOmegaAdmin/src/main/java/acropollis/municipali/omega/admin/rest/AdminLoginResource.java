package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerCredentials;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerToken;
import acropollis.municipali.omega.common.exceptions.HttpCredentialsViolationException;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Api(tags = "Login", description = "PROTECTED")
public class AdminLoginResource {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @PostMapping("")
    public CustomerToken login(@RequestBody CustomerCredentials customerCredentials) {
        return adminAuthenticationService
                .login(customerCredentials)
                .orElseThrow(() -> new HttpCredentialsViolationException(""));
    }

    @DeleteMapping("")
    public void logoff(@RequestBody String authToken) {
        adminAuthenticationService.logoff(authToken);
    }
}
