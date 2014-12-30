package com.ctv.conference.core;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.model.ConferenceModel;

import static com.ctv.conference.core.ConferenceErrorCode.CONFERENCE_NOT_FOUND;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferenceServiceImpl implements ConferenceService {

    private ConferencePersistenceAdapter persistenceAdapter;

    public ConferenceServiceImpl(ConferencePersistenceAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }

    @Override
    public ConferenceModel createConference(ConferenceModel conference) {
        return persistenceAdapter.createConference(conference);
    }

    @Override
    public void archiveConference(Integer conferenceId, Integer userId) {
        persistenceAdapter.isConferenceOwnedByUser(conferenceId, userId);
        persistenceAdapter.archiveConference(conferenceId);
    }

    @Override
    public ConferenceModel findConference(Integer id) {
        ConferenceModel conference = persistenceAdapter.findConference(id);
        if (conference == null) {
            throw new ResourceNotFoundException(CONFERENCE_NOT_FOUND);
        }
        return conference;
    }
}
