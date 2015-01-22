package com.ctv.conference.core;

import com.ctv.conference.core.adapter.MeetupPersistenceAdapter;
import com.ctv.conference.core.adapter.TalkPersistenceAdapter;

import java.util.Optional;

import static com.ctv.conference.core.ConferenceErrorCode.ACCESS_TO_MEETUP_DENIED;
import static com.ctv.conference.core.ConferenceErrorCode.ACCESS_TO_TALK_DENIED;
import static com.ctv.conference.core.ConferenceErrorCode.CONFERENCE_ID_NULL;
import static java.util.Objects.nonNull;

/**
 * @author Dmitry Kovalchuk
 */
public class ValidationServiceImpl implements ValidationService {

    private MeetupPersistenceAdapter meetupPersistenceAdapter;
    private TalkPersistenceAdapter talkPersistenceAdapter;


    void idMustBeNull(Integer id, ConferenceErrorCode errorCode) {
        if (nonNull(id)) {
            throw new DataConflictExceptions(errorCode);
        }
    }

    void idMustBeNotNull(Integer id, ConferenceErrorCode errorCode) {
        Optional.ofNullable(id)
                .orElseThrow(() -> new DataConflictExceptions(errorCode));
    }

    void validateOrganizerOrSpeaker(Integer talkId, Integer userId) {
        if (meetupPersistenceAdapter.isMeetupOwnedByUser(talkId, userId) || meetupPersistenceAdapter.isUserSpeakerOfMeetup(talkId, userId)) {
            throw new PermissionDeniedException(ACCESS_TO_TALK_DENIED);
        }
    }

    void validateMeetupAccessory(Integer meetupId, Integer userId) {
        if (!meetupPersistenceAdapter.isMeetupOwnedByUser(meetupId, userId)) {
            throw new PermissionDeniedException(ACCESS_TO_MEETUP_DENIED);
        }
    }

    void sopeakerOfMeetup(Integer meetupId, Integer userId) {
        if (meetupPersistenceAdapter.isUserSpeakerOfMeetup(meetupId, userId)) {
            throw new PermissionDeniedException(CONFERENCE_ID_NULL);
        }
    }


}
