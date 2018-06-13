package acropollis.municipali.omega.user.utils.log;

import org.apache.log4j.Logger;

public class LogUtils {
    private static final Logger articlesReloadLogger = Logger.getLogger("articlesReload");
    private static final Logger userAnswersReloadLogger = Logger.getLogger("userAnswersReload");
    private static final Logger pendingAnswersPersistJobLogger = Logger.getLogger("pendingAnswersPersistJob");

    public static Logger getArticlesReloadLogger() {
        return articlesReloadLogger;
    }   

    public static Logger getUserAnswersReloadLogger() {
        return userAnswersReloadLogger;
    }

    public static Logger getPendingAnswersPersistLogger() {
        return pendingAnswersPersistJobLogger;
    }
}
