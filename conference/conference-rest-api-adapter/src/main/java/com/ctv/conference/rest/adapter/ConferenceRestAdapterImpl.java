package com.ctv.conference.rest.adapter;

import com.ctv.conference.core.ConferenceService;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferenceRestAdapterImpl implements ConferenceRestAdapter {

    private ConferenceService conferenceService;

    public ConferenceRestAdapterImpl(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

}
