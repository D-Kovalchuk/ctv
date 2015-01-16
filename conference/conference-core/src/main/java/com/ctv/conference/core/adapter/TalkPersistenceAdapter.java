package com.ctv.conference.core.adapter;

import com.ctv.conference.core.model.Talk;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface TalkPersistenceAdapter {

    Talk createTalk(Talk talk, Integer meetId, Integer userId) ;

    Talk getTalk(Integer id);

    List<Talk> getTalks();

    Talk updateTalk(Talk talk);

    Talk hideTalk(Talk talk);

    void archiveTalk(Integer id);

    void assignSpeaker(Integer talkId, Integer userId);

}
