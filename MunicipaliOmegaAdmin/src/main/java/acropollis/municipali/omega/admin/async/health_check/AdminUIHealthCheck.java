package acropollis.municipali.omega.admin.async.health_check;

import acropollis.municipali.omega.admin.data.health_check.AdminHealth;
import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.async.CommonHealthCheck;
import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

@Service
public class AdminUIHealthCheck extends CommonHealthCheck<AdminHealth, AdminHealth.AdminUIHealth> {
    @Autowired
    private HealthCheckCache healthCheckCache;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional(readOnly = true)
    public void reload() {
        long t1 = new Date().getTime();

        String adminUIVersionPath = PropertiesConfig.config.getAdminUiVersionPath();

        try (FileReader fr = new FileReader(new File(adminUIVersionPath))) {
            JSONObject versionJson = (JSONObject) new JSONParser().parse(fr);

            String version = (String) versionJson.get("version");

            onReloadSuccess(t1, new Date().getTime())
                    .getAdminUIHealth()
                    .setAdminUIVersion(version);
        } catch (Exception e) {
            onReloadFailure(t1, new Date().getTime(), e);
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
    protected AdminHealth.AdminUIHealth getReloadJobHealthEntity() {
        return new AdminHealth.AdminUIHealth();
    }

    @Override
    protected void updateReloadJobHealth(AdminHealth adminHealth, AdminHealth.AdminUIHealth reloadJobHealth) {
        adminHealth.setAdminUIHealth(reloadJobHealth);
    }
}
