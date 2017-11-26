package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.health_check.async.HostingHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.HostingHealth;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserHostingHealthCheck extends HostingHealthCheck<UserHealth> {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void reload() {
        execute();
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
    protected HostingHealth getReloadJobHealthEntity() {
        return new HostingHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserHealth userHealth, HostingHealth hostingHealth) {
        userHealth.setHostingHealth(hostingHealth);
    }
}
