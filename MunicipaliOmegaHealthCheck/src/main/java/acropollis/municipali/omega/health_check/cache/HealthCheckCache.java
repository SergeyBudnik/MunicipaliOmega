package acropollis.municipali.omega.health_check.cache;

import acropollis.municipali.omega.health_check.data.CommonHealth;

import java.util.Optional;

public interface HealthCheckCache {
    void saveHealth(CommonHealth commonHealth);
    Optional<CommonHealth> getHealth();
}
