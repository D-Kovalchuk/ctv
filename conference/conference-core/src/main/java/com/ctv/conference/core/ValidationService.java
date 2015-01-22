package com.ctv.conference.core;

/**
 * @author Dmitry Kovalchuk
 */
public interface ValidationService {

    void checkIdOnNull(Integer id, ConferenceErrorCode errorCode);

    void checkIdOnNonNull(Integer id, ConferenceErrorCode errorCode);

    void validateTalkAccessory(Integer talkId, Integer userId);

    void validateMeetupAccessory(Integer meetupId, Integer userId);

    void checkSpeakerRole(Integer meetupId, Integer userId);

    void validateConferenceAccessory(Integer conferenceId, Integer userId);

    void checkResourceOnNull(Object model, ConferenceErrorCode errorCode);
}
