package com.ctv.registration.core.exception;

/**
 * @author Timur Yarosh
 */
public class DataConflictException extends CoreException {

    private static final int ERROR_CODE = 1103;

    public DataConflictException(String message) {
        super(message);
    }

    public DataConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getErrorCode() {
        return super.getErrorCode();
    }
}
