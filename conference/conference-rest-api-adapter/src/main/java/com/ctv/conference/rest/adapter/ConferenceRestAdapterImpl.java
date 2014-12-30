package com.ctv.conference.rest.adapter;

import com.ctv.conference.core.ConferenceService;
import com.ctv.conference.core.model.ConferenceModel;
import com.ctv.conference.rest.adapter.dto.ConferenceDto;
import org.springframework.core.convert.ConversionService;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferenceRestAdapterImpl implements ConferenceRestAdapter {

    private ConferenceService conferenceService;

    private ConversionService conversionService;

    public ConferenceRestAdapterImpl(ConferenceService conferenceService, ConversionService conversionService) {
        this.conferenceService = conferenceService;
        this.conversionService = conversionService;
    }

    @Override
    public ConferenceDto createConference(ConferenceDto conferenceDto) {
        ConferenceModel conference = conversionService.convert(conferenceDto, ConferenceModel.class);
        ConferenceModel savedConference = conferenceService.createConference(conference);
        return conversionService.convert(savedConference, ConferenceDto.class);
    }

}
