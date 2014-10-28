package com.ctv.registration.core.exception;

/**
 * @author Timur Yarosh
 */
public class CoreException extends RuntimeException {

    /**
     * ERROR_CODE consists of 4 digits:<br/>
     * <ol>
     * <li>service code</li>
     * <li>module code</li>
     * <li>exception code</li>
     * <li>exception code</li>
     * </ol>
     */
    private static final int ERROR_CODE = 1100;

    public CoreException(String message) {
        super(message);
    }

    public CoreException() {
        super();
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
