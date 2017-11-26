package acropollis.municipali.omega.health_check.async;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.data.HostingHealth;
import org.apache.commons.net.ftp.FTPClient;

import java.util.Date;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

public abstract class HostingHealthCheck<H extends CommonComponentHealth>
        extends CommonHealthCheck<H, HostingHealth> {

    public void execute() {
        onReloadStarted();

        long startDate = new Date().getTime();

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(
                    config.getImageHostingFtpUrl().getValue(),
                    (int) ((long) config.getImageHostingFtpPort().getValue())
            );

            boolean result = ftpClient.login(
                    config.getImageHostingFtpUsername().getValue(),
                    config.getImageHostingFtpPassword().getValue()
            );

            if (result) {
                onReloadSuccess(startDate, new Date().getTime());
            } else {
                throw new RuntimeException("Login to hosting failed");
            }
        } catch (Exception e) {
            onReloadFailure(startDate, new Date().getTime(), e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                /* Do nothing */
            }
        }
    }
}
