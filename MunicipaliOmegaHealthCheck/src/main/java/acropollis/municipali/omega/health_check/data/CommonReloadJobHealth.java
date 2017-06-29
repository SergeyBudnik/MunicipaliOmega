package acropollis.municipali.omega.health_check.data;

import lombok.Data;

@Data
public class CommonReloadJobHealth {
    private boolean health;
    private long duration;
    private long lastUpdateDate;
    private long durationSincePenultimateUpdate;
    private String message;
}
