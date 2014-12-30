package com.ctv.conference.persistence.adapter;

import com.ctv.conference.persistence.adapter.dto.ConferenceDto;

/**
 * @author Dmitry Kovalchuk
 */
public interface ConferenceRepository {

    ConferenceDto createConference(ConferenceDto conferenceDto);

}
