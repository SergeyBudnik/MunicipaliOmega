package acropollis.municipali.omega.health_check.async;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.data.DatabaseHealth;
import com.zaxxer.hikari.HikariPoolMXBean;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Date;

public abstract class DatabaseHealthCheck<H extends CommonComponentHealth> extends CommonHealthCheck<H, DatabaseHealth> {
    protected final void execute(String poolName) {
        long t1 = new Date().getTime();

        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectPoolName = new ObjectName("com.zaxxer.hikari:type=Pool (" + poolName + ")");
            HikariPoolMXBean poolProxy = JMX.newMBeanProxy(mBeanServer, objectPoolName, HikariPoolMXBean.class);

            int idleConnections = poolProxy.getIdleConnections();
            int activeConnections = poolProxy.getActiveConnections();
            int totalConnections = poolProxy.getTotalConnections();
            int threadsAwaitingConnections = poolProxy.getThreadsAwaitingConnection();

            DatabaseHealth databaseHealth = onReloadSuccess(t1, new Date().getTime()).getDatabaseHealth(); {
                databaseHealth.setIdleConnections(idleConnections);
                databaseHealth.setActiveConnections(activeConnections);
                databaseHealth.setTotalConnections(totalConnections);
                databaseHealth.setThreadsAwaitingConnections(threadsAwaitingConnections);
            }
        } catch (Exception e) {
            onReloadFailure(t1, new Date().getTime(), e);
        }
    }
}
