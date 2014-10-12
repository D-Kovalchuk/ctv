package com.ctv.registration.rest;

import com.ctv.registration.core.exception.UserIdNotFoundException;
import com.ctv.registration.core.exception.UsernameAlreadyExistsException;
import com.ctv.registration.rest.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Timur Yarosh
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler
    @ResponseBody
    public ErrorInfo handleUserIdNotFoundException(UserIdNotFoundException e) {
        int errorCode = NOT_FOUND.value();
        String errorMessage = e.getMessage();
        return new ErrorInfo(errorCode, errorMessage);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler
    @ResponseBody
    public ErrorInfo handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        int errorCode = CONFLICT.value();
        String errorMessage = e.getMessage();
        return new ErrorInfo(errorCode, errorMessage);
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler
    @ResponseBody
    public ErrorInfo handleAnyException(AuthenticationException e) {
        int errorCode = HttpStatus.UNAUTHORIZED.value();
        String errorMessage = e.getMessage();
        return new ErrorInfo(errorCode, errorMessage);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    @ResponseBody
    public ErrorInfo handleAnyException(Exception e) {
        int errorCode = INTERNAL_SERVER_ERROR.value();
        String errorMessage = e.getMessage();
        return new ErrorInfo(errorCode, errorMessage);
    }

}
