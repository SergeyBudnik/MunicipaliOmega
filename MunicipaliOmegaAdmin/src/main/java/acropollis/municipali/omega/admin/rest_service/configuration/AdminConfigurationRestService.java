package acropollis.municipali.omega.admin.rest_service.configuration;

import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;

public interface AdminConfigurationRestService {
    LanguageConfiguration getInterfaceLanguage();
    LanguageConfiguration getPlatformLanguage();
}
