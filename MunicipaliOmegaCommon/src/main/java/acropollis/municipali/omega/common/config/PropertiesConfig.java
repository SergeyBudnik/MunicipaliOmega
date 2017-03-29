package acropollis.municipali.omega.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class PropertiesConfig {
    public static Config config;

    static {
        String configHome = System.getenv("CONFIG_HOME");

        if (configHome == null) {
            configHome = System.getProperty("CONFIG_HOME");
        }

        String configFilePath =
                configHome +
                File.separator + "municipali-omega" +
                File.separator + "application.conf";

        config = ConfigFactory.parseFile(new File(configFilePath));
    }
}
