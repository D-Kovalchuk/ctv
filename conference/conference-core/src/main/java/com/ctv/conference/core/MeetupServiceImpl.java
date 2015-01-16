package com.ctv.conference.core;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.adapter.MeetupPersistenceAdapter;
import com.ctv.conference.core.model.Meetup;

import java.util.List;
import java.util.Optional;

import static com.ctv.conference.core.ConferenceErrorCode.MEETUP_ID_NOT_NULL;
import static com.ctv.conference.core.ConferenceErrorCode.MEETUP_ID_NULL;
import static com.ctv.conference.core.ConferenceErrorCode.MEETUP_NOT_FOUND;

/**
 * @author Dmitry Kovalchuk
 */
//todo intercept DataAccessException and propagate it
//todo move PermissionDeniedException to this class
public class MeetupServiceImpl implements MeetupService {

    private MeetupPersistenceAdapter meetupPersistenceAdapter;
    private ConferencePersistenceAdapter conferencePersistenceAdapter;

    public MeetupServiceImpl(MeetupPersistenceAdapter meetupPersistenceAdapter, ConferencePersistenceAdapter conferencePersistenceAdapter) {
        this.meetupPersistenceAdapter = meetupPersistenceAdapter;
        this.conferencePersistenceAdapter = conferencePersistenceAdapter;
    }

    @Secure
    @Override
    public Meetup createMeetup(Meetup meetup, Integer confId, Integer userId) {
        if (meetup.getId() != null) {
            throw new DataConflictExceptions(MEETUP_ID_NULL);
        }
        conferencePersistenceAdapter.isConferenceOwnedByUser(confId, userId);
        return meetupPersistenceAdapter.createMeetup(meetup, confId);
    }

    @Secure
    @Override
    public Meetup updateMeetup(Meetup meetup, Integer userId) {
        Optional.ofNullable(meetup.getId())
                .orElseThrow(() -> new DataConflictExceptions(MEETUP_ID_NOT_NULL));
        meetupPersistenceAdapter.isMeetupOwnedByUser(meetup.getId(), userId);
        return meetupPersistenceAdapter.updateMeetup(meetup);
    }

    @Override
    public List<Meetup> findMeetupsByConferenceId(Integer conferenceId) {
        return meetupPersistenceAdapter.findMeetupsByConferenceId(conferenceId);
    }

    @Override
    public Meetup findMeetup(Integer meetupId) {
        Meetup meetup = meetupPersistenceAdapter.findMeetup(meetupId);
        Optional.ofNullable(meetup)
                .orElseThrow(() -> new ResourceNotFoundException(MEETUP_NOT_FOUND));
        return meetup;
    }

    @Secure
    @Override
    public void hideMeetup(Integer meetupId, Integer userId) {
        meetupPersistenceAdapter.isMeetupOwnedByUser(meetupId, userId);
        Meetup meetup = findMeetup(meetupId);
        meetup.setHidden(true);
        meetupPersistenceAdapter.hideMeetup(meetup);
    }

    @Override
    public void joinAsSpeaker(Integer meetupId, Integer userId) {
        Meetup meetup = findMeetup(meetupId);
        meetup.getSpeakers().add(userId);
        meetupPersistenceAdapter.joinAsSpeaker(meetup);
    }

    @Secure
    @Override
    public void archiveMeetup(Integer meetupId, Integer userId) {
        meetupPersistenceAdapter.isMeetupOwnedByUser(meetupId, userId);
        Meetup meetup = findMeetup(meetupId);
        meetup.setDeleted(true);
        meetupPersistenceAdapter.archiveMeetup(meetup);
    }
}
