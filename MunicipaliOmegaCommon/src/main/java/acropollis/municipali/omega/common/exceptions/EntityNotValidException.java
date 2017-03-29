package acropollis.municipali.omega.common.exceptions;

public class EntityNotValidException extends RuntimeException {
    public EntityNotValidException(String message, Object... args) {
        super(String.format(message, args));
    }
}
