package acropollis.municipali.omega.user_notification.data.health_check;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import acropollis.municipali.omega.health_check.data.CommonReloadJobHealth;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserNotificationHealth extends CommonHealth {
    private boolean databaseHealth;
    private CommonReloadJobHealth releasedArticlesNotificationReloadJobHealth;

    public UserNotificationHealth() {
        setVersion(PropertiesConfig.config.getVersion().getValue());
    }
}
