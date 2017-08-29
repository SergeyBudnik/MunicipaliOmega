package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.rest_service.configuration.AdminConfigurationRestService;
import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;
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
public class AdminConfigurationResource {
    @AllArgsConstructor
    @Data
    private static class ImageHostingInfo {
        private String url;
    }

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

    @GetMapping("/image-hosting")
    public ImageHostingInfo getImageHostingRoot() {
        return new ImageHostingInfo(
                config.getImageHostingHttpUrl().getValue() + "/" + config.getId().getValue()
        );
    }
}
