package acropollis.municipali.omega.database.db.utils.log;

import lombok.Getter;
import org.apache.log4j.Logger;

import static org.apache.log4j.Logger.getLogger;

public class LogUtils {
    @Getter private static final Logger articlesServiceDatabaseLogger = getLogger("articlesServiceDatabase");
    @Getter private static final Logger articlesServiceImageHostingLogger = getLogger("articlesServiceImageHosting");
}
