package acropollis.municipali.omega.common.config;

import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.common.env.Environment;
import com.bdev.smart.config.SmartConfig;
import com.bdev.smart.config.SmartConfigProperties;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.List;

public class PropertiesConfig {
    public static SmartConfig config;

    static {
        Config instanceConfig = getInstanceConfig();

        config = SmartConfigProperties.getConfig(instanceConfig.getString("env"));

        config.getId().override(instanceConfig.getString("id"));

        if (config.getDatabaseRemoteConnection().getValue()) {
            config.getConnectionHost().override(instanceConfig.getString("connection.host"));
            config.getConnectionUsername().override(instanceConfig.getString("connection.username"));
            config.getConnectionPassword().override(instanceConfig.getString("connection.password"));
        }

        config.getDatabaseUrl().override(instanceConfig.getString("database.url"));
        config.getDatabaseUsername().override(instanceConfig.getString("database.username"));
        config.getDatabasePassword().override(instanceConfig.getString("database.password"));

        config.getClientAdminInterfaceDefaultLanguage().override(instanceConfig.getString("client.admin.interface.defaultLanguage"));
        config.getClientAdminInterfaceLanguages().override(instanceConfig.getStringList("client.admin.interface.languages"));

        config.getPlatformDefaultLanguage().override(instanceConfig.getString("platform.defaultLanguage"));
        config.getPlatformLanguages().override(instanceConfig.getStringList("platform.languages"));

        config.getGmsKeys().override((List<String>) instanceConfig.getAnyRefList("gms.keys"));
    }

    public static Language getLanguage() {
        return Language.fromName(config.getPlatformDefaultLanguage().getValue());
    }

    private static Config getInstanceConfig() {
        String configHome = System.getenv("CONFIG_HOME");

        if (configHome == null) {
            configHome = System.getProperty("CONFIG_HOME");
        }

        String configFilePath =
                configHome +
                        File.separator + (Environment.isTest() ? "municipali-omega-test" : "municipali-omega") +
                        File.separator + "application.conf";

        return ConfigFactory.parseFile(new File(configFilePath));
    }
}
