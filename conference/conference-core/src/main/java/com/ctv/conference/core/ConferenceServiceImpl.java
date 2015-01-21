package com.ctv.conference.core;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.model.ConferenceModel;

import static com.ctv.conference.core.ConferenceErrorCode.ACCESS_TO_CONFERENCE_DENIED;
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
    public ConferenceModel createConference(ConferenceModel conference, Integer userId) {
        //todo check that conference id == null
        //todo must be some limitation of number of conference which one user can create
        conference.setUserId(userId);
        return persistenceAdapter.createConference(conference);
    }

    @Override
    public void archiveConference(Integer conferenceId, Integer userId) {
        if (!persistenceAdapter.isConferenceOwnedByUser(conferenceId, userId)) {
            throw new PermissionDeniedException(ACCESS_TO_CONFERENCE_DENIED);
        }
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

    @Override
    public ConferenceModel updateConference(ConferenceModel conference, Integer userId) {
        if (conference.getId() == null) {
            throw new DataConflictExceptions(ConferenceErrorCode.CONFERENCE_ID_NULL);
        }
        if (!persistenceAdapter.isConferenceOwnedByUser(conference.getId(), userId)) {
            throw new PermissionDeniedException(ACCESS_TO_CONFERENCE_DENIED);
        }
        return persistenceAdapter.updateConference(conference);
    }

}
