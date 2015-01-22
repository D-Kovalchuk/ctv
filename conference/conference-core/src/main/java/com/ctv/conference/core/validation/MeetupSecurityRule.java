package com.ctv.conference.core.validation;

/**
 * @author Dmitry Kovalchuk
 */
public interface MeetupSecurityRule {

    //todo find meet up by id then find conference by user id
    boolean isMeetupOwnedByUser(Integer meetupId, Integer userId);

}
