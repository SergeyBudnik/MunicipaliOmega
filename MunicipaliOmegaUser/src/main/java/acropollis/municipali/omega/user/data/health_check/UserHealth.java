package acropollis.municipali.omega.user.data.health_check;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import acropollis.municipali.omega.health_check.data.CommonReloadJobHealth;
import acropollis.municipali.omega.health_check.data.MemoryHealth;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserHealth extends CommonHealth {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ArticlesReloadJobHealth extends CommonReloadJobHealth {
        private int amount;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PendingAnswersPersistJobHealth extends CommonReloadJobHealth {
        private int amount;
    }

    private ArticlesReloadJobHealth articlesReloadJobHealth;
    private PendingAnswersPersistJobHealth pendingAnswersPersistJobHealth;
    private MemoryHealth memoryHealth;
    private CommonReloadJobHealth brandingReloadJobHealth;
    private CommonReloadJobHealth statisticsReloadJobHealth;
    private CommonReloadJobHealth hostingHealth;

    public UserHealth() {
        setVersion(PropertiesConfig.config.getVersion().getValue());
    }
}
