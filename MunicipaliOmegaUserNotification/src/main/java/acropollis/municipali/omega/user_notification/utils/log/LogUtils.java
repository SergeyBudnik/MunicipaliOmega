package acropollis.municipali.omega.user_notification.utils.log;

import org.apache.log4j.Logger;

public class LogUtils {
    public static Logger getReleasedArticlesNotificationJobLogger() {
        return Logger.getLogger("releasedArticlesNotification");
    }
}
