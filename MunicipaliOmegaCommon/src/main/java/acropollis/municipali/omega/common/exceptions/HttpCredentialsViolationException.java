package acropollis.municipali.omega.common.exceptions;

public class HttpCredentialsViolationException extends RuntimeException {
    public HttpCredentialsViolationException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
