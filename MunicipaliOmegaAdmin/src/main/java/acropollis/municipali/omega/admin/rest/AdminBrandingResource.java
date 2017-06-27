package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.common.dto.common.Pair;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.branding.AdminBrandingRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/branding")
@Api(tags = "Branding", description = "PROTECTED")
public class AdminBrandingResource {
    @Autowired
    @Qualifier(Qualifiers.REQUEST_PROCESSING)
    private AdminBrandingRestService adminBrandingRestService;
    
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;
    
    @GetMapping(value = "/background/{w}/{h}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] getBackground(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable int w,
            @PathVariable int h
    ) {
        return adminBrandingRestService.getBackground(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken), w, h
        );
    }

    @PostMapping(value = "/background", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void setBackground(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody byte [] background
    ) {
        adminBrandingRestService.setBackground(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                Collections.singletonMap(new Pair<>(-1, -1), background)
        );
    }

    @DeleteMapping("/background")
    public void removeBackground(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        adminBrandingRestService.removeBackground(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken)
        );
    }

    @GetMapping(value = "/icon/{size}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] getIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable int size
    ) {
        return adminBrandingRestService.getIcon(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken), size
        );
    }

    @PostMapping(value = "/icon", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void setIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody byte [] icon
    ) {
        adminBrandingRestService.setIcon(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                Collections.singletonMap(new Pair<>(-1, -1), icon)
        );
    }

    @DeleteMapping(value = "/icon")
    public void removeIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        adminBrandingRestService.removeIcon(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken)
        );
    }
}
