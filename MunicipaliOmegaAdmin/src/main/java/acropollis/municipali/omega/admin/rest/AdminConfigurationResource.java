package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.rest_service.configuration.AdminConfigurationRestService;
import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
@Api(tags = "Configuration", description = " ")
public class AdminConfigurationResource {
    @Autowired
    private AdminConfigurationRestService adminConfigurationRestService;

    @GetMapping("/language/interface")
    public LanguageConfiguration getInterfaceLanguage() {
        return adminConfigurationRestService.getInterfaceLanguage();
    }

    @GetMapping("/language/platform")
    public LanguageConfiguration getPlatformLanguage() {
        return adminConfigurationRestService.getPlatformLanguage();
    }
}
