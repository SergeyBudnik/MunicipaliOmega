package acropollis.municipali.omega.admin.data.health_check;

import acropollis.municipali.omega.common.config.PropertiesConfig;
import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.data.CommonHealth;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.json.JSONObject;

import java.nio.file.FileSystems;

import static java.nio.file.Files.lines;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminHealth extends CommonComponentHealth {
    private String adminUIVersion;
    private CommonHealth hostingHealth;

    public AdminHealth() {
        setVersion(PropertiesConfig.config.getVersion().getValue());

        adminUIVersion = getAdminUIVersion();
    }

    private String getAdminUIVersion() {
        try {
            String versionJson = lines(
                    FileSystems.getDefault().getPath(
                            PropertiesConfig.config.getAdminUiVersionPath().getValue()
                    )
            ).reduce((s1, s2) -> s1 + "\n" + s2).orElse("");

            return (String) new JSONObject(versionJson).get("version");
        } catch (Exception e) {
            return null;
        }
    }
}
