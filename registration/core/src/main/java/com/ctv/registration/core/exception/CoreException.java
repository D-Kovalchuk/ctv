package com.ctv.registration.core.exception;

/**
 * @author Timur Yarosh
 */
public class CoreException extends RuntimeException {

    private final int errorCode;

    public CoreException(ErrorData errorData) {
        super(errorData.getMessage());
        errorCode = errorData.getCode();
    }

    public CoreException(ErrorData errorData, Throwable cause) {
        super(errorData.getMessage(), cause);
        errorCode = errorData.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

}
