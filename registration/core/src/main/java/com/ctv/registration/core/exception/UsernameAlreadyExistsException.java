package com.ctv.registration.core.exception;

import static java.lang.String.format;

/**
 * @author Timur Yarosh
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    private static final String PATTERN = "The username '%s' is already in use";

    public UsernameAlreadyExistsException(String username) {
        super(format(PATTERN, username));
    }
}
