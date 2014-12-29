package com.ctv.conference.core;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferenceServiceImpl implements ConferenceService {

    private ConferencePersistenceAdapter persistenceAdapter;

    public ConferenceServiceImpl(ConferencePersistenceAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }
}
