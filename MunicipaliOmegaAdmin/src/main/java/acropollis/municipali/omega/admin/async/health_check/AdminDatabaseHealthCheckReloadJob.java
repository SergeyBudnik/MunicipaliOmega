package acropollis.municipali.omega.admin.async.health_check;

import acropollis.municipali.omega.admin.data.health_check.AdminHealth;
import acropollis.municipali.omega.database.db.dao.CustomerDao;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminDatabaseHealthCheckReloadJob {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Autowired
    private CustomerDao customerDao;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        AdminHealth adminHealth = (AdminHealth) healthCheckCache.getHealth().orElse(new AdminHealth());

        adminHealth.setDatabaseHealth(getDatabaseHealth());

        healthCheckCache.saveHealth(adminHealth);
    }

    private boolean getDatabaseHealth() {
        try {
            return customerDao.count() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
