package com.ctv.conference.core;

import com.ctv.shared.model.ErrorCode;

/**
 * @author Dmitry Kovalchuk
 */
public enum ConferenceErrorCode implements ErrorCode {

    CORE_ERROR("Core error", 2100),

    CONFERENCE_NOT_FOUND("Conference not found", 2101),
    CONFERENCE_ID_NULL("Conference id mustn't be null", 2102),
    MEETUP_ID_NOT_NULL("Meetup id mustn't be null", 2103),
    MEETUP_ID_NULL("Meetup id mustn be null", 2104),
    MEETUP_NOT_FOUND("Meetup not found", 2105),
    ACCESS_TO_CONFERENCE_DENIED("Access to conference has been denied", 2106),
    ACCESS_TO_MEETUP_DENIED("Access to meetup has been denied", 2107);


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
