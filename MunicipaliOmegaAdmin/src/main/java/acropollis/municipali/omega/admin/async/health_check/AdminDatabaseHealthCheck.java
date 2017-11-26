package acropollis.municipali.omega.admin.async.health_check;

import acropollis.municipali.omega.admin.data.health_check.AdminHealth;
import acropollis.municipali.omega.health_check.async.DatabaseHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.DatabaseHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static acropollis.municipali.omega.admin.config.AdminDatabaseConfig.POOL_NAME;

@Service
public class AdminDatabaseHealthCheck extends DatabaseHealthCheck<AdminHealth> {
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
    protected AdminHealth getHealthEntity() {
        return new AdminHealth();
    }

    @Override
    protected DatabaseHealth getReloadJobHealthEntity() {
        return new DatabaseHealth();
    }

    @Override
    protected void updateReloadJobHealth(AdminHealth userNotificationHealth, DatabaseHealth reloadJobHealth) {
        userNotificationHealth.setDatabaseHealth(reloadJobHealth);
    }
}
