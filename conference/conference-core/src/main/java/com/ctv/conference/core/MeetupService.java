package com.ctv.conference.core;

import com.ctv.conference.core.model.Meetup;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface MeetupService {

//     1 check whether conference was created by user with user_id
//     2 check whether meetup have id if it have then throw an exception
    //todo 3 check date. have to be future
    Meetup createMeetup(Meetup meetup, Integer confId, Integer userId);

    //1 check whether Meetup id != null
    //2 check whether user own this meetup
    //todo 3 check whether date is not past
    // 4 update meetup
    Meetup updateMeetup(Meetup meetup, Integer userId);

    //todo if conference does not exist then exception must be thrown
    List<Meetup> findMeetupsByConferenceId(Integer conferenceId);

    //if meetup does not exist then exception must be thrown
    Meetup findMeetup(Integer meetupId);

    //set hide flag to true
    void hideMeetup(Integer meetupId, Integer userId);

    //todo ckeck whether meetup owned by user
    //todo check whether meetup exists
    //todo assign user as a speaker for this meetup thus he has all permissions to any kind of content of this meetup

    //todo consider to not set speaker immediately
    void joinAsSpeaker(Integer meetupId, Integer userId);

    List<Integer> getSpeakerPool(Integer meetupId, Integer userId);

    //todo set deleted flag to true
    void archiveMeetup(Integer meetupId, Integer userId);

}
