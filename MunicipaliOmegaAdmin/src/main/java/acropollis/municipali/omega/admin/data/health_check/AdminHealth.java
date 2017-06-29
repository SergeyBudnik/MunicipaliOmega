package acropollis.municipali.omega.admin.data.health_check;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminHealth extends CommonHealth {
    private boolean databaseHealth;

    public AdminHealth() {
        setVersion(PropertiesConfig.config.getVersion().getValue());
    }
}
