package acropollis.municipali.omega.user.async;

import acropollis.municipali.omega.database.db.dao.CustomerDao;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.user.data.health_check.UserHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDatabaseHealthCheckReloadJob {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Autowired
    private CustomerDao customerDao;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        UserHealth userHealth = (UserHealth) healthCheckCache.getHealth().orElse(new UserHealth());

        userHealth.setDatabaseHealth(getDatabaseHealth());

        healthCheckCache.saveHealth(userHealth);
    }

    private boolean getDatabaseHealth() {
        try {
            return customerDao.count() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
