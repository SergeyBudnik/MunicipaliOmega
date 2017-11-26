package acropollis.municipali.omega.health_check.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryHealth extends CommonHealth {
    public interface Container {
        MemoryHealth getMemoryHealth();
        void setMemoryHealth();
    }

    private long allocatedMemory;
    private long usedMemory;
    private long maxMemory;
}