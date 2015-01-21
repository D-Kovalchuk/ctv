package com.ctv.conference.core;

import com.ctv.conference.core.model.Talk;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
//todo consider where payment module have to be used
public interface TalkService {

    //todo who crete talk ? can create only speakers or conference owner
    //todo check talkId have to be null
    //todo add userId field to Talk model
    //todo check whether this user is speaker or not
    //todo set user id to this talk
    //todo validate date
    //todo save Talk
    Talk createTalk(Talk talk, Integer meetId, Integer userId) ;

    //todo full info
    Talk findTalk(Integer id);

    //todo brief info. to clarify
    List<Talk> findTalks();

    //todo talk id have to be not null
    //todo validate date
    Talk updateTalk(Talk talk);

    Talk hideTalk(Talk talk);

    void archiveTalk(Integer id);

    void assignSpeaker(Integer talkId, Integer userId);

}
