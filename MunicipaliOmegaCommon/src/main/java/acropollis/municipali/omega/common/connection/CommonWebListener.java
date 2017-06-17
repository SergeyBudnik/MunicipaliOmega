package acropollis.municipali.omega.common.connection;

import acropollis.municipali.omega.common.config.PropertiesConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CommonWebListener implements ServletContextListener {
    private static final boolean IS_REMOTE =
            PropertiesConfig.config.getDatabaseRemoteConnection().getValue();

    private SshConnection connection;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (IS_REMOTE) {
            try {
                connection = new SshConnection();
            } catch (Exception e) {
                /* ToDo: log */
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (IS_REMOTE) {
            connection.close();
        }
    }
}
