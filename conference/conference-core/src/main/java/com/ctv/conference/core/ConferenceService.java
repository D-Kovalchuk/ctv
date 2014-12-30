package com.ctv.conference.core;

import com.ctv.conference.core.model.ConferenceModel;

/**
 * @author Dmitry Kovalchuk
 */
public interface ConferenceService {

    ConferenceModel createConference(ConferenceModel conference);

}