package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;
import acropollis.municipali.omega.user.rest_service.configuration.UserConfigurationRestService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@RestController
@RequestMapping("/configuration")
@Api(tags = "Configuration", description = " ")
public class UserConfigurationResource {
    @AllArgsConstructor
    @Data
    private static class ImageHostingInfo {
        private String url;
    }

    @Autowired
    private UserConfigurationRestService userConfigurationRestService;

    @GetMapping("/language/platform")
    public LanguageConfiguration getPlatformLanguage() {
        return userConfigurationRestService.getPlatformLanguage();
    }

    @GetMapping("/image-hosting")
    public ImageHostingInfo getImageHostingRoot() {
        return new ImageHostingInfo(
                config.getImageHostingHttpUrl().getValue() + "/" + config.getId().getValue()
        );
    }
}
