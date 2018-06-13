package acropollis.municipali.omega.admin.utils.log;

import lombok.Getter;
import org.apache.log4j.Logger;

import static org.apache.log4j.Logger.getLogger;

public class LogUtils {
    @Getter private static final Logger articlesResourceLogger = getLogger("articlesResource");
}
