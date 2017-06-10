package acropollis.municipali.omega.user.rest_service.configuration;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;
import org.springframework.stereotype.Service;

@Service
public class UserConfigurationRestServiceImpl implements UserConfigurationRestService {
    @Override
    public LanguageConfiguration getPlatformLanguage() {
        return LanguageConfiguration.fromStrings(
                PropertiesConfig.config.getPlatformDefaultLanguage().getValue(),
                PropertiesConfig.config.getPlatformLanguages().getValue()
        );
    }
}
