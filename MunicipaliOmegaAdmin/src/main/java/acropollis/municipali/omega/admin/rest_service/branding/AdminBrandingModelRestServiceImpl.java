package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.common.dto.common.Tuple;
import acropollis.municipali.omega.common.dto.customer.CustomerInfo;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;
import static acropollis.municipali.omega.common.utils.storage.StandaloneImageStorageUtils.*;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminBrandingModelRestServiceImpl implements AdminBrandingRestService {
    @Override
    public byte [] getBackground(CustomerInfo user, int w, int h) {
        return
                getImage(getBackgroundLocation(), w, h)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Override
    public void setBackground(CustomerInfo user, Map<Tuple<Integer, Integer>, byte[]> background) {
        saveImages(getBackgroundLocation(), background);
    }

    @Override
    public void removeBackground(CustomerInfo user) {
        removeImages(getBackgroundLocation());
    }

    @Override
    public byte [] getIcon(CustomerInfo user, int size) {
        return
                getImage(getIconLocation(), size, size)
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }

    @Override
    public void setIcon(CustomerInfo user, Map<Tuple<Integer, Integer>, byte []> icon) {
        saveImages(getIconLocation(), icon);
    }

    @Override
    public void removeIcon(CustomerInfo user) {
        removeImages(getIconLocation());
    }

    private String getIconLocation() {
        return config.getImagesBrandingIconLocation().getValue();
    }

    private String getBackgroundLocation() {
        return config.getImagesBrandingBackgroundLocation().getValue();
    }
}
