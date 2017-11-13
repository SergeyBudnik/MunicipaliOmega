package acropollis.municipali.omega.admin.rest_service.views;

import acropollis.municipali.omega.database.db.dao.ArticleViewDao;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminViewsRestServiceImpl implements AdminViewsRestService {
    @Autowired
    private ArticleViewDao articleViewDao;

    @Transactional(readOnly = true)
    @Override
    public long getArticleViewsAmount(MunicipaliUserInfo userInfo, long articleId) {
        return articleViewDao.countArticleViewsByArticleId(articleId);
    }
}
