package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.user.rest_service.branding.BrandingRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branding")
@Api(tags = "Branding", description = " ")
public class UserBrandingResource {
    @Autowired
    private BrandingRestService brandingRestService;

    @RequestMapping(value = "/icon/{size}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] getIcon(@PathVariable int size) {
        return brandingRestService.getIcon(size);
    }

    @RequestMapping(value = "/background/{w}/{h}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] getBackground(@PathVariable int w, @PathVariable int h) {
        return brandingRestService.getBackground(w, h);
    }
}
