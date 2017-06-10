package acropollis.municipali.omega.admin.rest_service.configuration;

import acropollis.municipali.omega.common.dto.configuration.LanguageConfiguration;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;

public interface AdminConfigurationRestService {
    LanguageConfiguration getInterfaceLanguage(CustomerInfo user);
    LanguageConfiguration getPlatformLanguage(CustomerInfo user);
}
