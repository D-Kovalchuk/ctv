package com.ctv.conference.core.adapter;

import com.ctv.conference.core.model.Meetup;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface MeetupPersistenceAdapter {

    //todo 3 save meetup
    //todo 3 find conference and set new meet to it
    //todo 4 save conference
    Meetup createMeetup(Meetup meetup, Integer confId);

    Meetup updateMeetup(Meetup meetup);

    //todo if conference does not exist then exception must be thrown
    List<Meetup> findMeetupsByConferenceId(Integer conferenceId);

    Meetup findMeetup(Integer meetupId);

    void hideMeetup(Meetup meetup);

    void joinAsSpeaker(Meetup meetup);

    void archiveMeetup(Meetup meetup);

    //todo find meet up by id then find conference by user id
    boolean isMeetupOwnedByUser(Integer meetupId, Integer userId);

    List<Integer> getSpeakerPool(Integer meetupId);
}
