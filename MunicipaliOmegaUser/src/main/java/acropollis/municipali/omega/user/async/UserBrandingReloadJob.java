package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.common.dto.common.Pair;
import acropollis.municipali.omega.common.utils.storage.StandaloneImageStorageUtils;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.async.CommonHealthcheckedJob;
import acropollis.municipali.omega.user.cache.branding.UserBrandingCache;
import acropollis.municipali.omega.health_check.data.CommonReloadJobHealth;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
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
public class UserBrandingReloadJob extends CommonHealthcheckedJob<UserHealth, CommonReloadJobHealth> {
    private static final Logger log = LogUtils.getBrandingReloadLogger();

    @Autowired
    private UserBrandingCache userBrandingCache;

    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    public void reload() {
        onReloadStarted();

        long reloadStartDate = new Date().getTime();

        try {
            Optional<Map<Pair<Integer, Integer>, byte []>> background = StandaloneImageStorageUtils
                    .getImages(config.getImagesBrandingBackgroundLocation().getValue());

            userBrandingCache.setBackground(background.orElse(new HashMap<>()));

            Optional<Map<Pair<Integer, Integer>, byte []>> icon = StandaloneImageStorageUtils
                    .getImages(config.getImagesBrandingIconLocation().getValue());

            userBrandingCache.setIcon(icon.orElse(new HashMap<>()));

            updateHealthWithSuccess(
                    reloadStartDate,
                    new Date().getTime()
            );
        } catch (Exception e) {
            updateHealthWithFailure(reloadStartDate, new Date().getTime(), e);
        }
    }

    @Override
    protected HealthCheckCache getHealthCheckCache() {
        return healthCheckCache;
    }

    @Override
    protected UserHealth getHealthEntity() {
        return new UserHealth();
    }

    @Override
    protected CommonReloadJobHealth getReloadJobHealthEntity() {
        return new CommonReloadJobHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserHealth userHealth, CommonReloadJobHealth reloadJobHealth) {
        userHealth.setBrandingReloadJobHealth(reloadJobHealth);
    }

    private void updateHealthWithSuccess(long startDate, long endDate) {
        onReloadSuccess(startDate, endDate);

        log.info(String.format("Branding reloaded in %d ms", endDate - startDate));
    }

    private void updateHealthWithFailure(long startDate, long endDate, Exception failureReason) {
        onReloadFailure(startDate, endDate, failureReason);

        log.error(String.format("Branding failed to reload in %d ms", endDate - startDate));
    }
}
