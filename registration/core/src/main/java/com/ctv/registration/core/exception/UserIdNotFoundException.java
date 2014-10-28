package com.ctv.registration.core.exception;

import static java.lang.String.format;

/**
 * @author Timur Yarosh
 */
public class UserIdNotFoundException extends CoreException {

    private static final int ERROR_CODE = 1101;
    public static final String PATTERN = "User with id '%s' not found";

    public UserIdNotFoundException(Integer id) {
        super(format(PATTERN, id));
    }

    @Override
    public int getErrorCode() {
        return ERROR_CODE;
    }
}
