package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.common.dto.common.Tuple;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.exceptions.EntityNotFoundException;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.utils.storage.StandaloneImageStorageUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminBrandingModelRestServiceImpl implements AdminBrandingRestService {
    @Override
    public byte [] getBackground(CustomerInfo user, int w, int h) {
        return StandaloneImageStorageUtils
                .getImage(config.getString("images.branding-background"), w, h)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Override
    public void setBackground(CustomerInfo user, Map<Tuple<Integer, Integer>, byte[]> background) {
        StandaloneImageStorageUtils
                .saveImages(config.getString("images.branding-background"), background);
    }

    @Override
    public byte [] getIcon(CustomerInfo user, int size) {
        return StandaloneImageStorageUtils
                .getImage(config.getString("images.branding-icon"), size, size)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Override
    public void setIcon(CustomerInfo user, Map<Tuple<Integer, Integer>, byte []> icon) {
        StandaloneImageStorageUtils
                .saveImages(config.getString("images.branding-icon"), icon);
    }
}
