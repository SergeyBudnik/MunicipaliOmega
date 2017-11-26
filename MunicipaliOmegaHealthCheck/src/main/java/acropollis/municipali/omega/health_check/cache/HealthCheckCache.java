package acropollis.municipali.omega.health_check.cache;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;

import java.util.Optional;

public interface HealthCheckCache {
    void saveHealth(CommonComponentHealth commonHealth);
    Optional<CommonComponentHealth> getHealth();
}
