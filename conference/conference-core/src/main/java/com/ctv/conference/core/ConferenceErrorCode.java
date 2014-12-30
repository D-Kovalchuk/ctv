package com.ctv.conference.core;

import com.ctv.shared.model.ErrorCode;

/**
 * @author Dmitry Kovalchuk
 */
public enum ConferenceErrorCode implements ErrorCode {

    CORE_ERROR("Core error", 2100),

    CONFERENCE_NOT_FOUND("Conference not found", 2101);

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

    private ConferenceErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}
