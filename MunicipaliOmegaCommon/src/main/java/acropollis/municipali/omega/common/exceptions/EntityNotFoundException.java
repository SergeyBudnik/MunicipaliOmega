package acropollis.municipali.omega.common.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }
}
