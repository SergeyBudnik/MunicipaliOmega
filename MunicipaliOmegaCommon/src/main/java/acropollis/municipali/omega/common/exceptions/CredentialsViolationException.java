package acropollis.municipali.omega.common.exceptions;

public class CredentialsViolationException extends RuntimeException {
    public CredentialsViolationException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
