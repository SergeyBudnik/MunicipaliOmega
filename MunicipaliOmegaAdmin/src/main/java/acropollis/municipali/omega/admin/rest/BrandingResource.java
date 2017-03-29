package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.common.dto.common.Tuple;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.admin.rest_service.branding.BrandingRestService;
import acropollis.municipali.omega.admin.service.authentication.AuthenticationService;
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
public class BrandingResource {
    @Autowired
    @Qualifier(Qualifiers.REQUEST_PROCESSING)
    private BrandingRestService brandingRestService;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @RequestMapping(value = "/background/{w}/{h}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] getBackground(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable int w,
            @PathVariable int h
    ) {
        return brandingRestService.getBackground(
                authenticationService.getCustomerInfoOrThrow(authToken), w, h
        );
    }

    @RequestMapping(value = "/background", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void setBackground(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody byte [] background
    ) {
        brandingRestService.setBackground(
                authenticationService.getCustomerInfoOrThrow(authToken),
                Collections.singletonMap(new Tuple<>(-1, -1), background)
        );
    }

    @RequestMapping(value = "/icon/{size}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] getIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable int size
    ) {
        return brandingRestService.getIcon(
                authenticationService.getCustomerInfoOrThrow(authToken), size
        );
    }

    @RequestMapping(value = "/icon", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void setIcon(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestBody byte [] icon
    ) {
        brandingRestService.setIcon(
                authenticationService.getCustomerInfoOrThrow(authToken),
                Collections.singletonMap(new Tuple<>(-1, -1), icon)
        );
    }
}
