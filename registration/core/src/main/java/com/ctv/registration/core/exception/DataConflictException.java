package com.ctv.registration.core.exception;

/**
 * @author Timur Yarosh
 */
public class DataConflictException extends CoreException {

    public DataConflictException(ErrorData errorData) {
        super(errorData);
    }

    public DataConflictException(ErrorData errorData, Throwable cause) {
        super(errorData, cause);
    }

}
