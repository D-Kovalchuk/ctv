package com.ctv.conference.core;

import com.ctv.conference.core.model.Talk;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
//todo consider where payment module have to be used
public interface TalkService {

    //todo who crete talk ? can create only speakers or conference owner
    // check talkId have to be null
    // add userId field to Talk model
    // check whether this user is speaker or not
    // set user id to this talk
    //todo validate date
    //save Talk
    Talk createTalk(Talk talk, Integer meetId, Integer userId) ;

    //todo full info
    Talk findTalk(Integer talkId, Integer userId);

    //todo brief info. to clarify
    List<Talk> findTalks(Integer meetupId);

    //todo talk id have to be not null
    //todo validate date
    Talk updateTalk(Talk talk, Integer userId);

    void hideTalk(Integer talkId, Integer userId);

    void archiveTalk(Integer talkId, Integer userId);

    void assignSpeaker(Integer talkId, Integer userId, List<Integer> speakers);

}
