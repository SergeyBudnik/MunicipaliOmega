package acropollis.municipali.omega.user_notification.cache;

import acropollis.municipali.omega.common.dto.health_check.HealthCheck;

import java.util.Optional;

public interface UserNotificationHealthCheckCache {
    Optional<HealthCheck> getHealthCheck();
    void updateHealthCheck(HealthCheck healthCheck);
}
