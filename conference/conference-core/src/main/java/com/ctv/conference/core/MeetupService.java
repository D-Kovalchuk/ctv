package com.ctv.conference.core;

import com.ctv.conference.core.model.Meetup;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface MeetupService {

    //todo 1 check whether conference was created by user with user_id
    //todo 2 check whether meetup have id if it have then throw an exception
    //todo 3 check date. have to be future
    //todo 3 save meetup
    //todo 3 find conference and set new meet to it
    //todo 4 save conference
    Meetup createMeetup(Meetup meetup, Integer confId, Integer userId);

    //todo 1 check whether Meetup id != null
    //todo 2 check whether user own this meetup
    //todo 3 check whether date is not past
    //todo 4 update meetup
    Meetup updateMeetup(Meetup meetup, Integer userId);

    //todo if conference does not exist then exception must be thrown
    List<Meetup> findMeetupsByConferenceId(Integer conferenceId);

    //todo if meetup does not exist then exception must be thrown
    Meetup findMeetup(Integer meetupId);

    //todo set hide flag to true
    void hideMeetup(Meetup meetup);

    //todo check whether user with specified id exists
    //todo ckeck whether meetup owned by user
    //todo check whether meetup exists
    //todo assign user as a speaker for this meetup thus he has all permissions to any kind of content of this meetup

    //todo consider to not set speaker immediately
    void joinAsSpeaker(Integer meetupId, Integer userId);

    //todo set deleted flag to true
    void archiveMeetup(Integer meetupId);

}
