package acropollis.municipali.omega.health_check.data;

import lombok.Data;

@Data
public abstract class CommonComponentHealth {
    private String version;
    private DatabaseHealth databaseHealth;
    private MemoryHealth memoryHealth;
}
