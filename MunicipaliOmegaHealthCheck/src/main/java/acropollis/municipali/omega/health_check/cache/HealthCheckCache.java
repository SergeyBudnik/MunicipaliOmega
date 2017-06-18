package acropollis.municipali.omega.health_check.cache;

import acropollis.municipali.omega.common.dto.health_check.HealthCheck;

import java.util.Optional;

public interface HealthCheckCache {
    void saveHealth(HealthCheck healthCheck);
    Optional<HealthCheck> getHealth();
}
