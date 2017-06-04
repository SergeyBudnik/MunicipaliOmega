package acropollis.municipali.omega.user.rest_service.configuration;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.language.Language;
import org.springframework.stereotype.Service;

@Service
public class UserConfigurationRestServiceImpl implements UserConfigurationRestService {
    @Override
    public Language getLanguage() {
        return PropertiesConfig.getLanguage();
    }
}
