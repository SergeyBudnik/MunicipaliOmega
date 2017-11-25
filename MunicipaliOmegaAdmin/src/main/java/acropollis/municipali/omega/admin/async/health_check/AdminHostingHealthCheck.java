package acropollis.municipali.omega.admin.async.health_check;

import acropollis.municipali.omega.admin.data.health_check.AdminHealth;
import acropollis.municipali.omega.health_check.async.CommonHealthcheckedJob;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.CommonReloadJobHealth;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Service
public class AdminHostingHealthCheck extends CommonHealthcheckedJob<AdminHealth, CommonReloadJobHealth> {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void reload() {
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

    @Override
    protected HealthCheckCache getHealthCheckCache() {
        return healthCheckCache;
    }

    @Override
    protected AdminHealth getHealthEntity() {
        return new AdminHealth();
    }

    @Override
    protected CommonReloadJobHealth getReloadJobHealthEntity() {
        return new CommonReloadJobHealth();
    }

    @Override
    protected void updateReloadJobHealth(AdminHealth adminHealth, CommonReloadJobHealth hostingHealth) {
        adminHealth.setHostingHealth(hostingHealth);
    }
}
