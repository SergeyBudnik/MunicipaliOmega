package acropollis.municipali.omega.common.connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.SneakyThrows;

import java.util.Properties;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

public class SshConnection {
    private Session session;

    @SneakyThrows
    public SshConnection () {
        JSch jsch = new JSch();

        String host = config.getConnectionHost().getValue();
        String login = config.getConnectionUsername().getValue();
        String password = config.getConnectionPassword().getValue();
        int port = (int) ((long) config.getConnectionPort().getValue());

        int databaseLocalPort = (int) ((long) config.getConnectionDatabaseLocalPort().getValue());
        int databaseRemotePort = (int) ((long) config.getConnectionDatabaseRemotePort().getValue());

        session = jsch.getSession(login, host, port); {
            Properties config = new Properties(); {
                config.put("StrictHostKeyChecking", "no");
            }

            session.setPassword(password);
            session.setConfig(config);

            session.connect();
        }

        session.setPortForwardingL(databaseLocalPort, host, databaseRemotePort);
    }

    public void close() {
        session.disconnect();
    }
}
