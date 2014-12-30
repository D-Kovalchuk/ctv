package com.ctv.conference.core.adapter;

import com.ctv.conference.core.model.ConferenceModel;

/**
 * @author Dmitry Kovalchuk
 */
public interface ConferencePersistenceAdapter {

    ConferenceModel createConference(ConferenceModel conference);

}
