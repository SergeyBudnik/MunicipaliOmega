package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.common.dto.common.Tuple;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;

import java.util.Map;

public interface BrandingRestService {
    byte [] getBackground(CustomerInfo user, int w, int h);
    void setBackground(CustomerInfo user, Map<Tuple<Integer, Integer>, byte[]> background);

    byte [] getIcon(CustomerInfo user, int size);
    void setIcon(CustomerInfo user, Map<Tuple<Integer, Integer>, byte[]> icon);
}
