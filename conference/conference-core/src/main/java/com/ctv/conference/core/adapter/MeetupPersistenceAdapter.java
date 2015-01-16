package com.ctv.conference.core.adapter;

import com.ctv.conference.core.model.Meetup;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface MeetupPersistenceAdapter {

    Meetup createMeetup(Meetup meetup, Integer confId);

    Meetup updateMeetup(Meetup meetup);

    List<Meetup> findMeetupsByConferenceId(Integer conferenceId);

    Meetup findMeetup(Integer meetupId);

    void hideMeetup(Meetup meetup);

    void joinAsSpeaker(Integer meetupId, Integer userId);

    void archiveMeetup(Integer meetupId);

}
