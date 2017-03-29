package acropollis.municipali.omega.common.exceptions;

public class EntityIllegalStateException extends RuntimeException {
    public EntityIllegalStateException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
