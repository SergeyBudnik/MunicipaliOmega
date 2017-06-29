package acropollis.municipali.omega.health_check.cache;

import acropollis.municipali.omega.health_check.data.CommonHealth;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HealthCheckCacheImpl implements HealthCheckCache {
    private AtomicReference<CommonHealth> healthCheck = new AtomicReference<>(null);

    @Override
    public void saveHealth(CommonHealth commonHealth) {
        this.healthCheck.set(commonHealth);
    }

    @Override
    public Optional<CommonHealth> getHealth() {
        return Optional.ofNullable(healthCheck.get());
    }
}
