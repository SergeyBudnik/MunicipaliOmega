package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;
import acropollis.municipali.omega.user.rest_service.configuration.UserConfigurationRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
@Api(tags = "Configuration", description = " ")
public class UserConfigurationResource {
    @Autowired
    private UserConfigurationRestService userConfigurationRestService;

    @GetMapping("/language/platform")
    public LanguageConfiguration getPlatformLanguage() {
        return userConfigurationRestService.getPlatformLanguage();
    }
}
