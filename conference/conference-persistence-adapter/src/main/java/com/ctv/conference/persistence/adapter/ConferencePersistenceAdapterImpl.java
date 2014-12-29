package com.ctv.conference.persistence.adapter;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferencePersistenceAdapterImpl implements ConferencePersistenceAdapter {

    private ConferenceRepository conferenceRepository;

    public ConferencePersistenceAdapterImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }


}
