package acropollis.municipali.omega.health_check.cache;

import acropollis.municipali.omega.common.dto.health_check.HealthCheck;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HealthCheckCacheImpl implements HealthCheckCache {
    private AtomicReference<HealthCheck> healthCheck = new AtomicReference<>(null);

    @Override
    public void saveHealth(HealthCheck healthCheck) {
        this.healthCheck.set(healthCheck);
    }

    @Override
    public Optional<HealthCheck> getHealth() {
        return Optional.ofNullable(healthCheck.get());
    }
}
