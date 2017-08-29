package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.common.Pair;
import acropollis.municipali.omega.database.db.service.image.ImageService;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
@Qualifier(Qualifiers.MODEL)
public class AdminBrandingModelRestServiceImpl implements AdminBrandingRestService {
    @Autowired
    private ImageService imageService;

    @Override
    public void setBackground(MunicipaliUserInfo userInfo, Map<Pair<Integer, Integer>, byte[]> background) {
        background.forEach((size, image) ->
                imageService.addImage(
                        config.getImagesBrandingBackgroundLocation().getValue(),
                        String.format("%dx%d", size.getX(), size.getY()),
                        image
                )
        );
    }

    @Override
    public void removeBackground(MunicipaliUserInfo userInfo) {
        imageService.removeAllImagesKeepDirectory(
                config.getImagesBrandingBackgroundLocation().getValue()
        );
    }

    @Override
    public void setIcon(MunicipaliUserInfo userInfo, Map<Pair<Integer, Integer>, byte []> icon) {
        icon.forEach((size, image) ->
                imageService.addImage(
                        config.getImagesBrandingIconLocation().getValue(),
                        String.format("%dx%d", size.getX(), size.getY()),
                        image
                )
        );
    }

    @Override
    public void removeIcon(MunicipaliUserInfo userInfo) {
        imageService.removeAllImagesKeepDirectory(
                config.getImagesBrandingIconLocation().getValue()
        );

    }
}
