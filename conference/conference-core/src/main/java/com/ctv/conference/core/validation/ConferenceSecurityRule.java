package com.ctv.conference.core.validation;

/**
 * @author Dmitry Kovalchuk
 */
public interface ConferenceSecurityRule {

    boolean isConferenceOwnedByUser(Integer conferenceId, Integer userId);

}
