package acropollis.municipali.omega.health_check.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryHealth extends CommonReloadJobHealth {
    private long allocatedMemory;
    private long usedMemory;
    private long maxMemory;
}