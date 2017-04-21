package acropollis.municipali.omega.common.exceptions;

public class HttpEntityNotValidException extends RuntimeException {
    public HttpEntityNotValidException(String message, Object... args) {
        super(String.format(message, args));
    }
}
