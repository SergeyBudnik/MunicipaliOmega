package acropollis.municipali.omega.user_notification.async;

import acropollis.municipali.omega.database.db.dao.CustomerDao;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.user_notification.data.health_check.UserNotificationHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNotificationDatabaseHealthCheckReloadJob {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Autowired
    private CustomerDao customerDao;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        UserNotificationHealth userNotificationHealth = (UserNotificationHealth) healthCheckCache.getHealth().orElse(new UserNotificationHealth());

        userNotificationHealth.setDatabaseHealth(getDatabaseHealth());

        healthCheckCache.saveHealth(userNotificationHealth);
    }

    private boolean getDatabaseHealth() {
        try {
            return customerDao.count() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
