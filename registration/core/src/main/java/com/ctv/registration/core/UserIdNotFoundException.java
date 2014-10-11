package com.ctv.registration.core;

import static java.lang.String.format;

/**
 * @author Timur Yarosh
 */
public class UserIdNotFoundException extends RuntimeException {

    public static final String PATTERN = "User with id '%s' not found";

    public UserIdNotFoundException(Integer id) {
        super(format(PATTERN, id));
    }
}
