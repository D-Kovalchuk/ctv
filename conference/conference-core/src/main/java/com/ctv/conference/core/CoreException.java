package com.ctv.conference.core;

import com.ctv.shared.model.ErrorCode;

/**
 * @author Dmitry Kovalchuk
 */
public class CoreException extends RuntimeException {

    private final int errorCode;

    public CoreException(ErrorCode errorData) {
        super(errorData.getMessage());
        errorCode = errorData.getCode();
    }

    public CoreException(ErrorCode errorData, Throwable cause) {
        super(errorData.getMessage(), cause);
        errorCode = errorData.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

}

