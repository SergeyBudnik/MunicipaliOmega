package acropollis.municipali.omega.health_check.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DatabaseHealth extends CommonHealth {
    public interface Container {
        DatabaseHealth getDatabaseHealth();
        void setDatabaseHealth(DatabaseHealth health);
    }

    private int idleConnections;
    private int activeConnections;
    private int totalConnections;
    private int threadsAwaitingConnections;
}
