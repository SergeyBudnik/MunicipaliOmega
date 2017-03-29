package acropollis.municipali.omega.common.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
