package com.ctv.conference.core;

import com.ctv.conference.core.adapter.MeetupPersistenceAdapter;
import com.ctv.conference.core.adapter.TalkPersistenceAdapter;
import com.ctv.conference.core.model.Talk;

import java.util.List;

import static com.ctv.conference.core.ConferenceErrorCode.*;

/**
 * @author Dmitry Kovalchuk
 */
//todo divide TalkPersistenceAdapter interface into two interfaces. first - is common functionality, second - interface validation methods
public class TalkServiceImpl implements TalkService {

    private MeetupPersistenceAdapter meetupPersistenceAdapter;
    private TalkPersistenceAdapter talkPersistenceAdapter;
    private ValidationServiceImpl validationService;

    public TalkServiceImpl(MeetupPersistenceAdapter meetupPersistenceAdapter, TalkPersistenceAdapter talkPersistenceAdapter) {
        this.meetupPersistenceAdapter = meetupPersistenceAdapter;
        this.talkPersistenceAdapter = talkPersistenceAdapter;
    }

    @Override
    public Talk createTalk(Talk talk, Integer meetId, Integer userId) {
        validationService.idMustBeNull(talk.getId(), MEETUP_ID_NOT_NULL);
        validationService.validateOrganizerOrSpeaker(meetId, userId);
        talk.setUserId(userId);
        return talkPersistenceAdapter.createTalk(talk);
    }

    @Override
    public Talk findTalk(Integer talkId, Integer userId) {
        //todo check whether this talk paid or free
        //PaymentService.hasHasPermission(talkId, userId)
        return talkPersistenceAdapter.findTalk(talkId);
    }

    @Override
    public List<Talk> findTalks(Integer meetupId) {
        return talkPersistenceAdapter.findTalks(meetupId);
    }

    @Override
    public Talk updateTalk(Talk talk, Integer userId) {
        validationService.idMustBeNotNull(talk.getId(), CONFERENCE_ID_NULL);
        Talk foundTalk = findTalk(talk.getId(), userId);
        validationService.validateOrganizerOrSpeaker(foundTalk.getId(), userId);
        return talkPersistenceAdapter.updateTalk(talk);
    }

    @Override
    public void hideTalk(Integer talkId, Integer userId) {
        Talk talk = findTalk(talkId, userId);
        validationService.validateMeetupAccessory(talk.getMeetupId(), userId);
        talk.setHidden(true);
        talkPersistenceAdapter.hideTalk(talk);
    }

    @Override
    public void archiveTalk(Integer talkId, Integer userId) {
        Talk talk = findTalk(talkId, userId);
        talk.setDeleted(true);
        talkPersistenceAdapter.archiveTalk(talk);
    }

    @Override
    public void assignSpeaker(Integer talkId, Integer userId, List<Integer> speakers) {
        Talk talk = findTalk(talkId, userId);
        speakers.forEach(e -> {
            validationService.sopeakerOfMeetup(talk.getMeetupId(), userId);
        });
        talk.setSpeakers(speakers);
        talkPersistenceAdapter.updateTalk(talk);
    }

}
