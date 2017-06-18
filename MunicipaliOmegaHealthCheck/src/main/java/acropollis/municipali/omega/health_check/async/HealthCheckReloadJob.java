package acropollis.municipali.omega.health_check.async;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.common.dto.health_check.HealthCheck;
import acropollis.municipali.omega.database.db.dao.CustomerDao;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class HealthCheckReloadJob {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedDelay = 5 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        boolean databaseHealth = customerDao.count() > 0;

        HealthCheck healthCheck = HealthCheck.builder()
                .version(PropertiesConfig.config.getVersion().getValue())
                .globalHealth(true)
                .databaseHealth(databaseHealth)
                .lastUpdateDate(new Date().getTime())
                .build();

        healthCheckCache.saveHealth(healthCheck);
    }
}
