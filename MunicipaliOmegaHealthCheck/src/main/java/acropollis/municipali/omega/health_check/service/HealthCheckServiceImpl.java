package acropollis.municipali.omega.health_check.service;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Override
    public Optional<CommonComponentHealth> getHealth() {
        return healthCheckCache.getHealth();
    }
}
