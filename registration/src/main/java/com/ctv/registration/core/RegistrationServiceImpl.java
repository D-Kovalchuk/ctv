package com.ctv.registration.core;

import com.ctv.registration.core.port.in.RegistrationPersistenceAdapter;

/**
 * @author Dmitry Kovalchuk
 */
public class RegistrationServiceImpl implements RegistrationService {

    private RegistrationPersistenceAdapter persistenceAdapter;

    public RegistrationServiceImpl(RegistrationPersistenceAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }


}
