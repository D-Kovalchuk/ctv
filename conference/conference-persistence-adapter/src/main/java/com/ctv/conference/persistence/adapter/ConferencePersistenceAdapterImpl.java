package com.ctv.conference.persistence.adapter;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.model.ConferenceModel;
import com.ctv.conference.persistence.adapter.dto.ConferenceDto;
import org.springframework.core.convert.ConversionService;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferencePersistenceAdapterImpl implements ConferencePersistenceAdapter {

    private ConferenceRepository conferenceRepository;

    private ConversionService conversionService;

    public ConferencePersistenceAdapterImpl(ConferenceRepository conferenceRepository, ConversionService conversionService) {
        this.conferenceRepository = conferenceRepository;
        this.conversionService = conversionService;
    }

    @Override
    public ConferenceModel createConference(ConferenceModel conference) {
        ConferenceDto conferenceDto = conversionService.convert(conference, ConferenceDto.class);
        ConferenceDto savedConference = conferenceRepository.createConference(conferenceDto);
        return conversionService.convert(savedConference, ConferenceModel.class);
    }
}
