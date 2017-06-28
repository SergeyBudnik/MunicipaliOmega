package acropollis.municipali.omega.user.async.branding;

import acropollis.municipali.omega.common.dto.common.Pair;
import acropollis.municipali.omega.common.utils.storage.StandaloneImageStorageUtils;
import acropollis.municipali.omega.user.cache.branding.UserBrandingCache;
import acropollis.municipali.omega.user.utils.log.LogUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
public class BrandingReloadJob {
    private static final Logger log = LogUtils.getBrandingReloadLogger();

    @Autowired
    private UserBrandingCache userBrandingCache;

    @Scheduled(fixedRate = 60 * 1000)
    public void reload() {
        long currentTime = new Date().getTime();

        try {
            Optional<Map<Pair<Integer, Integer>, byte []>> background = StandaloneImageStorageUtils
                    .getImages(config.getImagesBrandingBackgroundLocation().getValue());

            userBrandingCache.setBackground(background.orElse(new HashMap<>()));

            Optional<Map<Pair<Integer, Integer>, byte []>> icon = StandaloneImageStorageUtils
                    .getImages(config.getImagesBrandingIconLocation().getValue());

            userBrandingCache.setIcon(icon.orElse(new HashMap<>()));

            log.info(String.format("Branding reloaded in %d ms", new Date().getTime() - currentTime));
        } catch (Exception e) {
            log.error(String.format("Branding failed to reload in %d ms", new Date().getTime() - currentTime));
        }
    }
}
