package com.ctv.conference.core;

import com.ctv.conference.core.adapter.MeetupPersistenceAdapter;
import com.ctv.conference.core.model.Meetup;

import java.util.List;

import static com.ctv.conference.core.ConferenceErrorCode.*;

/**
 * @author Dmitry Kovalchuk
 */
//todo intercept DataAccessException and propagate it
public class MeetupServiceImpl implements MeetupService {

    private MeetupPersistenceAdapter meetupPersistenceAdapter;
    private ValidationService validationService;

    public MeetupServiceImpl(MeetupPersistenceAdapter meetupPersistenceAdapter, ValidationService validationService) {
        this.meetupPersistenceAdapter = meetupPersistenceAdapter;
        this.validationService = validationService;
    }

    @Override
    public Meetup createMeetup(Meetup meetup, Integer confId, Integer userId) {
        validationService.checkIdOnNull(meetup.getId(), MEETUP_ID_NULL);
        validationService.validateConferenceAccessory(confId, userId);
        return meetupPersistenceAdapter.createMeetup(meetup, confId);
    }

    @Override
    public Meetup updateMeetup(Meetup meetup, Integer userId) {
        validationService.checkIdOnNonNull(meetup.getId(), MEETUP_ID_NOT_NULL);
        validationService.validateMeetupAccessory(meetup.getId(), userId);
        return meetupPersistenceAdapter.updateMeetup(meetup);
    }

    @Override
    public List<Meetup> findMeetupsByConferenceId(Integer conferenceId) {
        return meetupPersistenceAdapter.findMeetupsByConferenceId(conferenceId);
    }

    @Override
    public Meetup findMeetup(Integer meetupId) {
        Meetup meetup = meetupPersistenceAdapter.findMeetup(meetupId);
        validationService.checkResourceOnNull(meetup, MEETUP_NOT_FOUND);
        return meetup;
    }

    @Override
    public void hideMeetup(Integer meetupId, Integer userId) {
        validationService.validateMeetupAccessory(meetupId, userId);
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

    @Override
    public List<Integer> getSpeakerPool(Integer meetupId, Integer userId) {
        validationService.validateMeetupAccessory(meetupId, userId);
        return meetupPersistenceAdapter.getSpeakerPool(meetupId);
    }

    @Override
    public void archiveMeetup(Integer meetupId, Integer userId) {
        validationService.validateMeetupAccessory(meetupId, userId);
        Meetup meetup = findMeetup(meetupId);
        meetup.setDeleted(true);
        meetupPersistenceAdapter.archiveMeetup(meetup);
    }

}
