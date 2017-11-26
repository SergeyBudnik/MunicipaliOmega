package acropollis.municipali.omega.health_check.cache;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HealthCheckCacheImpl implements HealthCheckCache {
    private AtomicReference<CommonComponentHealth> healthCheck = new AtomicReference<>(null);

    @Override
    public void saveHealth(CommonComponentHealth commonHealth) {
        this.healthCheck.set(commonHealth);
    }

    @Override
    public Optional<CommonComponentHealth> getHealth() {
        return Optional.ofNullable(healthCheck.get());
    }
}
