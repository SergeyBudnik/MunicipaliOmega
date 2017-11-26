package acropollis.municipali.omega.user_notification.data.health_check;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserNotificationHealth extends CommonComponentHealth {
    private CommonHealth releasedArticlesNotificationReloadJobHealth;

    public UserNotificationHealth() {
        setVersion(PropertiesConfig.config.getVersion().getValue());
    }
}
