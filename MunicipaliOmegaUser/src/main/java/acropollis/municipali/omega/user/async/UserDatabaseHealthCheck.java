package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.health_check.async.DatabaseHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.DatabaseHealth;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static acropollis.municipali.omega.user.config.UserDatabaseConfig.POOL_NAME;

@Service
public class UserDatabaseHealthCheck extends DatabaseHealthCheck<UserHealth> {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        execute(POOL_NAME);
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
    protected DatabaseHealth getReloadJobHealthEntity() {
        return new DatabaseHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserHealth userNotificationHealth, DatabaseHealth reloadJobHealth) {
        userNotificationHealth.setDatabaseHealth(reloadJobHealth);
    }
}
