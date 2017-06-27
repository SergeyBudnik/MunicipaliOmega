package acropollis.municipali.omega.common.log;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class CommonLogger extends RollingFileAppender {
    public CommonLogger() {
        PatternLayout layout = new PatternLayout(); {
            layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
        }

        setMaxFileSize("5MB");
        setMaxBackupIndex(10);
        setLayout(layout);
    }
}
