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

    @ExceptionHandler(HttpEntityNotFoundException.class)
    public void handleEntityNotFound(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(HttpEntityAlreadyExistsException.class)
    public void handleEntityAlreadyExists(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(HttpEntityIllegalStateException.class)
    public void handleEntityIllegalState(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(HttpEntityNotValidException.class)
    public void handleEntityNotValid(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(e.getMessage());
    }

    @ExceptionHandler(HttpCredentialsViolationException.class)
    public void handleCredentialsViolation(HttpServletResponse response, Exception e) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(e.getMessage());
    }
}