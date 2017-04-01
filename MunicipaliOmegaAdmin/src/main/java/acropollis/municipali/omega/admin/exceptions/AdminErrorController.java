package acropollis.municipali.omega.admin.exceptions;

import acropollis.municipali.omega.common.exceptions.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AdminErrorController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFound(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public void handleEntityAlreadyExists(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(EntityIllegalStateException.class)
    public void handleEntityIllegalState(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(EntityNotValidException.class)
    public void handleEntityNotValid(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(CredentialsViolationException.class)
    public void handleCredentialsViolation(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(e.getMessage());
    }
}