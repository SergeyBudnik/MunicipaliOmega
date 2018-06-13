package acropollis.municipali.omega.user_notification.utils.log;

import org.apache.log4j.Logger;

public class LogUtils {
    private static final Logger releasedArticlesNotificationLogger = Logger.getLogger("releasedArticlesNotification");

    public static Logger getReleasedArticlesNotificationJobLogger() {
        return releasedArticlesNotificationLogger;
    }
}
