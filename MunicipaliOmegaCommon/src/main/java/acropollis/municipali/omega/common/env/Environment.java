package acropollis.municipali.omega.common.env;

import java.util.Arrays;
import java.util.List;

public class Environment {
    public static boolean isTest() {
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
