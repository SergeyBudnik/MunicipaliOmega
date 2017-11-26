package acropollis.municipali.omega.admin.async.health_check;

import acropollis.municipali.omega.admin.data.health_check.AdminHealth;
import acropollis.municipali.omega.health_check.async.HostingHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.HostingHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminHostingHealthCheck extends HostingHealthCheck<AdminHealth> {
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
    protected AdminHealth getHealthEntity() {
        return new AdminHealth();
    }

    @Override
    protected HostingHealth getReloadJobHealthEntity() {
        return new HostingHealth();
    }

    @Override
    protected void updateReloadJobHealth(AdminHealth adminHealth, HostingHealth hostingHealth) {
        adminHealth.setHostingHealth(hostingHealth);
    }
}
