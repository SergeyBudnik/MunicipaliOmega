package acropollis.municipali.omega.common.exceptions;

public class HttpEntityNotFoundException extends RuntimeException {
    public HttpEntityNotFoundException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
