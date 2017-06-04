package acropollis.municipali.omega.admin.data.dto.health_check;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HealthCheck {
    private boolean globalHealth;
    private boolean databaseHealth;
    private long lastUpdateDate;
}
