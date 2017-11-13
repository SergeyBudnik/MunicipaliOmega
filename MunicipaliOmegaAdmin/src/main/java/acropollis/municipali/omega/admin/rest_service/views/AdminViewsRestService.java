package acropollis.municipali.omega.admin.rest_service.views;

import acropollis.municipali.security.common.dto.MunicipaliUserInfo;

public interface AdminViewsRestService {
    long getArticleViewsAmount(MunicipaliUserInfo userInfo, long articleId);
}
