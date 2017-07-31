package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.common.dto.common.Pair;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;

import java.util.Map;

public interface AdminBrandingRestService {
    byte [] getBackground(MunicipaliUserInfo userInfo, int w, int h);
    void setBackground(MunicipaliUserInfo userInfo, Map<Pair<Integer, Integer>, byte[]> background);
    void removeBackground(MunicipaliUserInfo userInfo);

    byte [] getIcon(MunicipaliUserInfo userInfo, int size);
    void setIcon(MunicipaliUserInfo userInfo, Map<Pair<Integer, Integer>, byte[]> icon);
    void removeIcon(MunicipaliUserInfo userInfo);
}
