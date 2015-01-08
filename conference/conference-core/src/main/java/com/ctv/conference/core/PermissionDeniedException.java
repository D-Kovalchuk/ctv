package com.ctv.conference.core;

/**
 * @author Dmitry Kovalchuk
 */
public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String message) {
        super(message);
    }

}
