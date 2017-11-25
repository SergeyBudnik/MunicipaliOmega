package acropollis.municipali.omega.health_check.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DatabaseHealth extends CommonReloadJobHealth {
    private int idleConnections;
    private int activeConnections;
    private int totalConnections;
    private int threadsAwaitingConnections;
}
