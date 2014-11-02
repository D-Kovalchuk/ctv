package com.ctv.registration.rest;

import com.ctv.registration.core.exception.CoreException;
import com.ctv.registration.core.exception.DataConflictException;
import com.ctv.registration.core.exception.ResourceNotFoundException;
import com.ctv.registration.rest.dto.ErrorInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.ctv.registration.core.exception.ErrorData.BAD_CREDENTIALS;
import static org.springframework.http.HttpStatus.*;

/**
 * @author Timur Yarosh
 */
@ResponseBody
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler
    public ErrorInfo handleUserIdNotFoundException(ResourceNotFoundException e) {
        return errorResponse(e);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler
    public ErrorInfo handleUsernameAlreadyExistsException(DataConflictException e) {
        return errorResponse(e);
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler
    public ErrorInfo handleAuthenticationException(AuthenticationException e) {
        int errorCode = BAD_CREDENTIALS.getCode();
        String errorMessage = BAD_CREDENTIALS.getMessage();
        return new ErrorInfo(errorCode, errorMessage);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorInfo handleAnyException(Exception e) {
        int errorCode = INTERNAL_SERVER_ERROR.value();
        String errorMessage = e.getMessage();
        return new ErrorInfo(errorCode, errorMessage);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler
    public ErrorInfo handleValidationException(MethodArgumentNotValidException e) {
        //todo consider validation violation messages
        return new ErrorInfo(CONFLICT.value(), e.getLocalizedMessage());
    }

    private ErrorInfo errorResponse(CoreException e) {
        int errorCode = e.getErrorCode();
        String errorMessage = e.getMessage();
        return new ErrorInfo(errorCode, errorMessage);
    }

}
