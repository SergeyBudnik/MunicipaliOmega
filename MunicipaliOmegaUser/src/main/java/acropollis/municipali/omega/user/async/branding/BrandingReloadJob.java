package acropollis.municipali.omega.user.async.branding;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.common.Tuple;
import acropollis.municipali.omega.user.cache.branding.BrandingCache;
import acropollis.municipali.omega.user.image.standalone.StandaloneImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static acropollis.municipali.omega.common.config.PropertiesConfig.*;

@Service
public class BrandingReloadJob {
    @Autowired
    private StandaloneImageStorageService standaloneImageStorageService;
    @Autowired
    private BrandingCache brandingCache;

    @Scheduled(fixedDelay = 5 * 1000)
    public void reload() {
        Optional<Map<Tuple<Integer, Integer>, byte []>> background = standaloneImageStorageService
                .getImages(config.getString("images.branding-background"));

        brandingCache.setBackground(background.orElse(new HashMap<>()));

        Optional<Map<Tuple<Integer, Integer>, byte []>> icon = standaloneImageStorageService
                .getImages(config.getString("images.branding-icon"));

        brandingCache.setIcon(icon.orElse(new HashMap<>()));
    }
}
