package acropollis.municipali.omega.common.exceptions;

public class HttpEntityIllegalStateException extends RuntimeException {
    public HttpEntityIllegalStateException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
