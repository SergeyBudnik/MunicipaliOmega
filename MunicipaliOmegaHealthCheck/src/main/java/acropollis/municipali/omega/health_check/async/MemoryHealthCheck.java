package acropollis.municipali.omega.health_check.async;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.data.MemoryHealth;

import java.util.Date;

import static java.lang.Runtime.getRuntime;

public abstract class MemoryHealthCheck<H extends CommonComponentHealth> extends CommonHealthCheck<H, MemoryHealth> {
    protected final void execute() {
        long time = new Date().getTime();

        try {
            long usedMemory = getRuntime().totalMemory() - getRuntime().freeMemory();
            long allocatedMemory = getRuntime().totalMemory();
            long maxMemory = getRuntime().maxMemory();

            MemoryHealth memoryHealth = onReloadSuccess(time, time).getMemoryHealth(); {
                memoryHealth.setUsedMemory(usedMemory);
                memoryHealth.setAllocatedMemory(allocatedMemory);
                memoryHealth.setMaxMemory(maxMemory);
            }
        } catch (Exception e) {
            onReloadFailure(time, new Date().getTime(), e);
        }
    }
}
