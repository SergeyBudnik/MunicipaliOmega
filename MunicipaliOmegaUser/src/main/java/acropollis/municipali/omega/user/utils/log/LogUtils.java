package acropollis.municipali.omega.user.utils.log;

import org.apache.log4j.Logger;

public class LogUtils {
    public static Logger getArticlesReloadLogger() {
        return Logger.getLogger("articlesReload");
    }

    public static Logger getUserAnswersReloadLogger() {
        return Logger.getLogger("userAnswersReload");
    }

    public static Logger getPendingAnswersPersistLogger() {
        return Logger.getLogger("pendingAnswersPersistJob");
    }
}
