package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.common.dto.common.Pair;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;

import java.util.Map;

public interface AdminBrandingRestService {
    byte [] getBackground(CustomerInfo user, int w, int h);
    void setBackground(CustomerInfo user, Map<Pair<Integer, Integer>, byte[]> background);
    void removeBackground(CustomerInfo user);

    byte [] getIcon(CustomerInfo user, int size);
    void setIcon(CustomerInfo user, Map<Pair<Integer, Integer>, byte[]> icon);
    void removeIcon(CustomerInfo user);
}
