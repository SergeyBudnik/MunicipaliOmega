package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.health_check.async.MemoryCommonHealthcheckedJob;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.MemoryHealth;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMemoryHealthCheckReloadJob extends MemoryCommonHealthcheckedJob<UserHealth> {
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
    protected UserHealth getHealthEntity() {
        return new UserHealth();
    }

    @Override
    protected MemoryHealth getReloadJobHealthEntity() {
        return new MemoryHealth();
    }

    @Override
    protected void updateReloadJobHealth(UserHealth userHealth, MemoryHealth reloadJobHealth) {
        userHealth.setMemoryHealth(reloadJobHealth);
    }
}
