package acropollis.municipali.omega.admin.rest_service.configuration;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;
import org.springframework.stereotype.Service;

@Service
public class AdminConfigurationRestServiceImpl implements AdminConfigurationRestService {
    @Override
    public LanguageConfiguration getInterfaceLanguage() {
        return LanguageConfiguration.fromStrings(
                PropertiesConfig.config.getClientAdminInterfaceDefaultLanguage().getValue(),
                PropertiesConfig.config.getClientAdminInterfaceLanguages().getValue()
        );
    }

    @Override
    public LanguageConfiguration getPlatformLanguage() {
        return LanguageConfiguration.fromStrings(
                PropertiesConfig.config.getPlatformDefaultLanguage().getValue(),
                PropertiesConfig.config.getPlatformLanguages().getValue()
        );
    }
}
