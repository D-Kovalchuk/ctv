package com.ctv.registration.core.exception;

import com.ctv.shared.model.ErrorCode;

/**
 * @author Timur Yarosh
 */
public class DataConflictException extends CoreException {

    public DataConflictException(ErrorCode errorData) {
        super(errorData);
    }

    public DataConflictException(ErrorCode errorData, Throwable cause) {
        super(errorData, cause);
    }

}
