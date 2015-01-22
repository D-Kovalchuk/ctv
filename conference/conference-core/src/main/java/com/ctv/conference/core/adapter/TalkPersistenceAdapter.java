package com.ctv.conference.core.adapter;

import com.ctv.conference.core.model.Talk;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface TalkPersistenceAdapter {

    Talk createTalk(Talk talk) ;

    Talk findTalk(Integer id);

    List<Talk> findTalks(Integer meetupId);

    Talk updateTalk(Talk talk);

    Talk hideTalk(Talk talk);

    void archiveTalk(Talk talk);

    void assignSpeaker(Integer talkId, Integer userId);

}
