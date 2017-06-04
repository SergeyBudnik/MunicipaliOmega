package acropollis.municipali.omega.admin.rest_service.configuration;

import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.dto.language.Language;

public interface AdminConfigurationRestService {
    Language getLanguage(CustomerInfo user);
}
