package acropollis.municipali.omega.user.data.health_check;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import acropollis.municipali.omega.health_check.data.CommonReloadJobHealth;
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

    private boolean databaseHealth;
    private ArticlesReloadJobHealth articlesReloadJobHealth;
    private PendingAnswersPersistJobHealth pendingAnswersPersistJobHealth;
    private CommonReloadJobHealth brandingReloadJobHealth;
    private CommonReloadJobHealth statisticsReloadJobHealth;

    public UserHealth() {
        setVersion(PropertiesConfig.config.getVersion().getValue());
    }
}
