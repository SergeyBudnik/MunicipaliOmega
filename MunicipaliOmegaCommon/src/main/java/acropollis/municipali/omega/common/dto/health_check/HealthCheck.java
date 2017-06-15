package acropollis.municipali.omega.common.dto.health_check;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HealthCheck {
    private boolean globalHealth;
    private boolean databaseHealth;
    private long lastUpdateDate;
}
