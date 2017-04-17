package acropollis.municipali.omega.user.async.branding;

import acropollis.municipali.omega.common.dto.common.Tuple;
import acropollis.municipali.omega.common.utils.storage.StandaloneImageStorageUtils;
import acropollis.municipali.omega.user.cache.branding.UserBrandingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
public class BrandingReloadJob {
    @Autowired
    private UserBrandingCache userBrandingCache;

    @Scheduled(fixedDelay = 5 * 1000)
    public void reload() {
        Optional<Map<Tuple<Integer, Integer>, byte []>> background = StandaloneImageStorageUtils
                .getImages(config.getString("images.branding-background"));

        userBrandingCache.setBackground(background.orElse(new HashMap<>()));

        Optional<Map<Tuple<Integer, Integer>, byte []>> icon = StandaloneImageStorageUtils
                .getImages(config.getString("images.branding-icon"));

        userBrandingCache.setIcon(icon.orElse(new HashMap<>()));
    }
}
