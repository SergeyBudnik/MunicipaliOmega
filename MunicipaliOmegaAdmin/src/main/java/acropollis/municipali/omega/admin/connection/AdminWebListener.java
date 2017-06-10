package acropollis.municipali.omega.admin.connection;

import acropollis.municipali.omega.common.connection.SshConnection;
import acropollis.municipali.omega.common.env.Environment;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AdminWebListener implements ServletContextListener {
    private SshConnection connection;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (!Environment.isTest()) {
            connection = new SshConnection();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (!Environment.isTest()) {
            connection.close();
        }
    }
}
