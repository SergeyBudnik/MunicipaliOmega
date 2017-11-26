package acropollis.municipali.omega.health_check.service;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;

import java.util.Optional;

public interface HealthCheckService {
    Optional<CommonComponentHealth> getHealth();
}
