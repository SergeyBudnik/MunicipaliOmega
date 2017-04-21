package acropollis.municipali.omega.common.exceptions;

public class HttpEntityAlreadyExistsException extends RuntimeException {
    public HttpEntityAlreadyExistsException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
