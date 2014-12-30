package com.ctv.conference.rest.adapter;

import com.ctv.conference.rest.adapter.dto.ConferenceDto;

/**
 * @author Dmitry Kovalchuk
 */
public interface ConferenceRestAdapter {

    ConferenceDto createConference(ConferenceDto conferenceDto);

    void archiveConference(Integer conferenceId, Integer userId);
}
