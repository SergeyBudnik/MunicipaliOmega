package acropollis.municipali.omega.admin.async.health_check;

import acropollis.municipali.omega.admin.data.health_check.AdminHealth;
import acropollis.municipali.omega.health_check.async.MemoryHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.MemoryHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminMemoryHealthCheck extends MemoryHealthCheck<AdminHealth> {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        execute();
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
    protected MemoryHealth getReloadJobHealthEntity() {
        return new MemoryHealth();
    }

    @Override
    protected void updateReloadJobHealth(AdminHealth adminHealth, MemoryHealth reloadJobHealth) {
        adminHealth.setMemoryHealth(reloadJobHealth);
    }
}
