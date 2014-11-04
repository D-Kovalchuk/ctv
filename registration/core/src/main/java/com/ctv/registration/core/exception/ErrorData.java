package com.ctv.registration.core.exception;

/**
 * @author Timur Yarosh
 */
public enum ErrorData {

    CORE_ERROR("Core error", 1100),
    USER_ID_NOT_FOUND("User id not found", 1101),
    USERNAME_ALREADY_EXISTS("The username is already in use", 1102),
    PAYLOAD_WITH_USER_ID("Payload mustn't contain user id", 1103),
    BAD_CREDENTIALS("Bad credentials", 1102),
    BAD_PASSWORD("Old password does not correct", 1104);

    private final String message;
    /**
     * ERROR_CODE consists of 4 digits:<br/>
     * <ol>
     * <li>service code</li>
     * <li>module code</li>
     * <li>exception code</li>
     * <li>exception code</li>
     * </ol>
     */
    private final int code;

    private ErrorData(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
