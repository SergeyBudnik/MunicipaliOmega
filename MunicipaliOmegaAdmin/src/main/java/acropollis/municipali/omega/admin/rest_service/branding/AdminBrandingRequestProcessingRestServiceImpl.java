package acropollis.municipali.omega.admin.rest_service.branding;

import acropollis.municipali.omega.common.dto.common.Tuple;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerInfo;
import acropollis.municipali.omega.admin.rest_service.Qualifiers;
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
    public byte [] getBackground(CustomerInfo user, int w, int h) {
        return adminBrandingRestService.getBackground(user, w, h);
    }

    @Override
    public void setBackground(CustomerInfo user, Map<Tuple<Integer, Integer>, byte[]> background) {
        try {
            BufferedImage src = fromBytes(Base64.getDecoder().decode(background.get(new Tuple<>(-1, -1))));

            Map<Tuple<Integer, Integer>, byte []> processedBackground = new HashMap<>();

            processedBackground.put(new Tuple<>( 240,  320), toBytes(scaleAndCropImage(src,  240,  320)));
            processedBackground.put(new Tuple<>( 320,  480), toBytes(scaleAndCropImage(src,  320,  480)));
            processedBackground.put(new Tuple<>( 480,  800), toBytes(scaleAndCropImage(src,  480,  800)));
            processedBackground.put(new Tuple<>( 768, 1280), toBytes(scaleAndCropImage(src,  768, 1280)));
            processedBackground.put(new Tuple<>(1080, 1920), toBytes(scaleAndCropImage(src, 1080, 1920)));
            processedBackground.put(new Tuple<>(1440, 2560), toBytes(scaleAndCropImage(src, 1440, 2560)));

            adminBrandingRestService.setBackground(user, processedBackground);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte [] getIcon(CustomerInfo user, int size) {
        return adminBrandingRestService.getIcon(user, size);
    }

    @Override
    public void setIcon(CustomerInfo user, Map<Tuple<Integer, Integer>, byte[]> icon) {
        try {
            BufferedImage src = fromBytes(Base64.getDecoder().decode(icon.get(new Tuple<>(-1, -1))));

            Map<Tuple<Integer, Integer>, byte []> processedIcon = new HashMap<>();

            processedIcon.put(new Tuple<>( 75,  75), toBytes(scaleImageByWidth(src, 75)));
            processedIcon.put(new Tuple<>(100, 100), toBytes(scaleImageByWidth(src, 100)));
            processedIcon.put(new Tuple<>(150, 150), toBytes(scaleImageByWidth(src, 150)));
            processedIcon.put(new Tuple<>(200, 200), toBytes(scaleImageByWidth(src, 200)));
            processedIcon.put(new Tuple<>(300, 300), toBytes(scaleImageByWidth(src, 300)));
            processedIcon.put(new Tuple<>(400, 400), toBytes(scaleImageByWidth(src, 400)));

            adminBrandingRestService.setIcon(user, processedIcon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

