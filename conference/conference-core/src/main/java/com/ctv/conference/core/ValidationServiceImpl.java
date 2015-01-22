package com.ctv.conference.core;

import com.ctv.conference.core.validation.ConferenceSecurityRule;
import com.ctv.conference.core.validation.MeetupSecurityRule;
import com.ctv.conference.core.validation.TalkSecurityRule;

import java.util.Optional;

import static com.ctv.conference.core.ConferenceErrorCode.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Dmitry Kovalchuk
 */
public class ValidationServiceImpl implements ValidationService {

    private MeetupSecurityRule meetupSecurityRule;
    private TalkSecurityRule talkSecurityRule;
    private ConferenceSecurityRule conferenceSecurityRule;

    public ValidationServiceImpl(ConferenceSecurityRule conferenceSecurityRule, MeetupSecurityRule meetupSecurityRule, TalkSecurityRule talkSecurityRule) {
        this.conferenceSecurityRule = conferenceSecurityRule;
        this.meetupSecurityRule = meetupSecurityRule;
        this.talkSecurityRule = talkSecurityRule;
    }

    @Override
    public void checkIdOnNull(Integer id, ConferenceErrorCode errorCode) {
        if (nonNull(id)) {
            throw new DataConflictExceptions(errorCode);
        }
    }

    @Override
    public void checkIdOnNonNull(Integer id, ConferenceErrorCode errorCode) {
        Optional.ofNullable(id)
                .orElseThrow(() -> new DataConflictExceptions(errorCode));
    }

    @Override
    public void validateTalkAccessory(Integer talkId, Integer userId) {
        if (!meetupSecurityRule.isMeetupOwnedByUser(talkId, userId) || !talkSecurityRule.isUserInSpeakerPool(talkId, userId)) {
            throw new PermissionDeniedException(ACCESS_TO_TALK_DENIED);
        }
    }

    @Override
    public void checkSpeakerRole(Integer meetupId, Integer userId) {
        if (!talkSecurityRule.isUserInSpeakerPool(meetupId, userId)) {
            throw new PermissionDeniedException(CONFERENCE_ID_NULL);
        }
    }

    @Override
    public void validateMeetupAccessory(Integer meetupId, Integer userId) {
        if (!meetupSecurityRule.isMeetupOwnedByUser(meetupId, userId)) {
            throw new PermissionDeniedException(ACCESS_TO_MEETUP_DENIED);
        }
    }

    @Override
    public void validateConferenceAccessory(Integer conferenceId, Integer userId) {
        if (!conferenceSecurityRule.isConferenceOwnedByUser(conferenceId, userId)) {
            throw new PermissionDeniedException(ACCESS_TO_CONFERENCE_DENIED);
        }
    }

    @Override
    public void checkResourceOnNull(Object model, ConferenceErrorCode errorCode) {
        if (isNull(model)) {
            throw new ResourceNotFoundException(errorCode);
        }
    }


}
