package acropollis.municipali.omega.admin.rest_service.configuration;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.language.Language;
import org.springframework.stereotype.Service;

@Service
public class AdminConfigurationRestServiceImpl implements AdminConfigurationRestService {
    @Override
    public Language getLanguage(CustomerInfo user) {
        return PropertiesConfig.getLanguage();
    }
}
