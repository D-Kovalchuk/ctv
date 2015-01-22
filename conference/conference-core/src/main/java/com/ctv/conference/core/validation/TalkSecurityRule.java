package com.ctv.conference.core.validation;

/**
 * @author Dmitry Kovalchuk
 */
public interface TalkSecurityRule {

    boolean isUserInSpeakerPool(Integer talkId, Integer userId);

}
