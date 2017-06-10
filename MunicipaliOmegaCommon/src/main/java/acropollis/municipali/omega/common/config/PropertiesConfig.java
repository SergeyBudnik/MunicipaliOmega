package acropollis.municipali.omega.common.config;

import acropollis.municipali.omega.common.dto.language.Language;
import acropollis.municipali.omega.common.env.Environment;
import com.bdev.smart.config.SmartConfig;
import com.bdev.smart.config.SmartConfigProperties;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PropertiesConfig {
    public static SmartConfig config;

    static {
        Config instanceConfig = getInstanceConfig();

        config = SmartConfigProperties.getConfig(instanceConfig.getString("env"));

        if (!Environment.isTest()) {
            config.getConnectionHost().override(instanceConfig.getString("connection.host"));
            config.getConnectionUsername().override(instanceConfig.getString("connection.username"));
            config.getConnectionPassword().override(instanceConfig.getString("connection.password"));
        }

        config.getDatabaseUrl().override(instanceConfig.getString("database.url"));
        config.getDatabaseUsername().override(instanceConfig.getString("database.username"));
        config.getDatabasePassword().override(instanceConfig.getString("database.password"));
    }

    public static Language getLanguage() {
        return Language.fromName(config.getLanguage().getValue());
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
