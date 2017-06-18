package acropollis.municipali.omega.health_check.service;

import acropollis.municipali.omega.common.dto.health_check.HealthCheck;

import java.util.Optional;

public interface HealthCheckService {
    Optional<HealthCheck> getHealth();
}
