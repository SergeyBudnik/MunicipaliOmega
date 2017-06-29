package acropollis.municipali.omega.health_check.service;

import acropollis.municipali.omega.health_check.data.CommonHealth;

import java.util.Optional;

public interface HealthCheckService {
    Optional<CommonHealth> getHealth();
}
