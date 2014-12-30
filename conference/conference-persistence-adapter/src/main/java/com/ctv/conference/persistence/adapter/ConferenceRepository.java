package com.ctv.conference.persistence.adapter;

import com.ctv.conference.persistence.adapter.dto.ConferenceDto;

/**
 * @author Dmitry Kovalchuk
 */
public interface ConferenceRepository {

    ConferenceDto createConference(ConferenceDto conferenceDto);

    void isConferenceOwnedByUser(Integer conferenceId, Integer userId);

    void archiveConference(Integer conferenceId);

    ConferenceDto findConference(Integer id);
}
