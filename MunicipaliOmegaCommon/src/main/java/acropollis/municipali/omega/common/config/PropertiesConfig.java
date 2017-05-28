package acropollis.municipali.omega.common.config;

import acropollis.municipali.omega.common.dto.language.Language;
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
        config = SmartConfigProperties.getConfig("dev");

        String configHome = System.getenv("CONFIG_HOME");

        if (configHome == null) {
            configHome = System.getProperty("CONFIG_HOME");
        }

        String configFilePath =
                configHome +
                File.separator + (isJUnitTest() ? "municipali-omega-test" : "municipali-omega") +
                File.separator + "application.conf";

        Config instanceConfig = ConfigFactory.parseFile(new File(configFilePath));

        config.getDatabaseUrl().override(instanceConfig.getString("database.url"));
        config.getDatabaseUsername().override(instanceConfig.getString("database.username"));
        config.getDatabasePassword().override(instanceConfig.getString("database.password"));
    }

    public static Language getLanguage() {
        return Language.fromName(config.getLanguage().getValue());
    }

    /* ToDo: move somewhere */
    private static boolean isJUnitTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> list = Arrays.asList(stackTrace);

        for (StackTraceElement element : list) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }

        return false;
    }
}
