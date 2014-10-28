package com.ctv.registration.core.exception;

import org.springframework.dao.DataAccessException;

import static java.lang.String.format;

/**
 * @author Timur Yarosh
 */
public class UsernameAlreadyExistsException extends DataConflictException {

    private static final int ERROR_CODE = 1102;
    private static final String PATTERN = "The username '%s' is already in use";

    public UsernameAlreadyExistsException(String username) {
        super(createErrorMessage(username));
    }

    public UsernameAlreadyExistsException(String username, DataAccessException e) {
        super(createErrorMessage(username), e);
    }

    private static String createErrorMessage(String username) {
        return format(PATTERN, username);
    }

    @Override
    public int getErrorCode() {
        return ERROR_CODE;
    }
}
