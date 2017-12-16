package acropollis.municipali.omega.user.data.health_check;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import acropollis.municipali.omega.health_check.data.MemoryHealth;
import acropollis.municipali.omega.health_check.data.ReloadHealth;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserHealth extends CommonComponentHealth {
    private ReloadHealth articlesReloadJobHealth;
    private ReloadHealth pendingAnswersPersistJobHealth;
    private MemoryHealth memoryHealth;
    private CommonHealth brandingReloadJobHealth;
    private CommonHealth statisticsReloadJobHealth;
    private CommonHealth hostingHealth;

    public UserHealth() {
        setVersion(PropertiesConfig.config.getVersion());
    }
}
