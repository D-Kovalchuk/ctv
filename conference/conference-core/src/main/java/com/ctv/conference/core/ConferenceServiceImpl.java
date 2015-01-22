package com.ctv.conference.core;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.model.ConferenceModel;

import static com.ctv.conference.core.ConferenceErrorCode.CONFERENCE_ID_NULL;
import static com.ctv.conference.core.ConferenceErrorCode.CONFERENCE_NOT_FOUND;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferenceServiceImpl implements ConferenceService {

    private ConferencePersistenceAdapter persistenceAdapter;
    private ValidationService validationService;

    public ConferenceServiceImpl(ConferencePersistenceAdapter persistenceAdapter, ValidationService validationService) {
        this.persistenceAdapter = persistenceAdapter;
        this.validationService = validationService;
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
        validationService.validateConferenceAccessory(conferenceId, userId);
        persistenceAdapter.archiveConference(conferenceId);
    }

    @Override
    public ConferenceModel findConference(Integer id) {
        ConferenceModel conference = persistenceAdapter.findConference(id);
        validationService.checkResourceOnNull(conference, CONFERENCE_NOT_FOUND);
        return conference;
    }

    @Override
    public ConferenceModel updateConference(ConferenceModel conference, Integer userId) {
        validationService.checkIdOnNonNull(conference.getId(), CONFERENCE_ID_NULL);
        validationService.validateConferenceAccessory(conference.getId(), userId);
        return persistenceAdapter.updateConference(conference);
    }

}
