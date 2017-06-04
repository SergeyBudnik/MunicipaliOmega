package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.rest_service.configuration.AdminConfigurationRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import acropollis.municipali.omega.common.dto.language.Language;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
@Api(tags = "Configuration", description = "PROTECTED")
public class AdminConfigurationResource {
    @Autowired
    private AdminConfigurationRestService adminConfigurationRestService;
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @GetMapping("/language")
    public Language getLanguage(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return adminConfigurationRestService.getLanguage(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken)
        );
    }
}
