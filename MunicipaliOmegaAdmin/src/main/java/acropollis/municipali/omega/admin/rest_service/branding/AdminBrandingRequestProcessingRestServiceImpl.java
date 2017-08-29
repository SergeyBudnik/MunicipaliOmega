package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.admin.rest_service.Qualifiers;
import acropollis.municipali.omega.common.dto.common.Pair;
import acropollis.municipali.security.common.dto.MunicipaliUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static acropollis.municipali.omega.common.utils.common.ImageUtils.*;

@Service
@Qualifier(Qualifiers.REQUEST_PROCESSING)
public class AdminBrandingRequestProcessingRestServiceImpl implements AdminBrandingRestService {
    @Autowired
    @Qualifier(Qualifiers.MODEL)
    private AdminBrandingRestService adminBrandingRestService;

    @Override
    public void setBackground(MunicipaliUserInfo userInfo, Map<Pair<Integer, Integer>, byte[]> background) {
        try {
            BufferedImage src = fromBytes(Base64.getDecoder().decode(background.get(new Pair<>(-1, -1))));

            Map<Pair<Integer, Integer>, byte []> processedBackground = new HashMap<>();

            processedBackground.put(new Pair<>( 160,  240), toBytes(scaleAndCropImage(src,  160,  240)));
            processedBackground.put(new Pair<>( 240,  400), toBytes(scaleAndCropImage(src,  240,  400)));
            processedBackground.put(new Pair<>( 386,  640), toBytes(scaleAndCropImage(src,  384,  640)));
            processedBackground.put(new Pair<>( 540,  960), toBytes(scaleAndCropImage(src,  540,  960)));
            processedBackground.put(new Pair<>( 720, 1280), toBytes(scaleAndCropImage(src,  720, 1280)));

            adminBrandingRestService.setBackground(userInfo, processedBackground);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBackground(MunicipaliUserInfo userInfo) {
        adminBrandingRestService.removeBackground(userInfo);
    }

    @Override
    public void setIcon(MunicipaliUserInfo userInfo, Map<Pair<Integer, Integer>, byte[]> icon) {
        try {
            BufferedImage src = fromBytes(Base64.getDecoder().decode(icon.get(new Pair<>(-1, -1))));

            Map<Pair<Integer, Integer>, byte []> processedIcon = new HashMap<>();

            processedIcon.put(new Pair<>(100, 100), toBytes(scaleImageByWidth(src, 100)));
            processedIcon.put(new Pair<>(150, 150), toBytes(scaleImageByWidth(src, 150)));
            processedIcon.put(new Pair<>(200, 200), toBytes(scaleImageByWidth(src, 200)));
            processedIcon.put(new Pair<>(300, 300), toBytes(scaleImageByWidth(src, 300)));
            processedIcon.put(new Pair<>(400, 400), toBytes(scaleImageByWidth(src, 400)));

            adminBrandingRestService.setIcon(userInfo, processedIcon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeIcon(MunicipaliUserInfo userInfo) {
        adminBrandingRestService.removeIcon(userInfo);
    }
}

