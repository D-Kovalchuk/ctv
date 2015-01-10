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
    public ConferenceDto createConference(ConferenceDto conferenceDto, Integer userId) {
        ConferenceModel conference = conversionService.convert(conferenceDto, ConferenceModel.class);
        ConferenceModel savedConference = conferenceService.createConference(conference, userId);
        return conversionService.convert(savedConference, ConferenceDto.class);
    }

    @Override
    public void archiveConference(Integer conferenceId, Integer userId) {
        conferenceService.archiveConference(conferenceId, userId);
    }

    @Override
    public ConferenceDto findConference(Integer id) {
        ConferenceModel conferenceModel = conferenceService.findConference(id);
        return conversionService.convert(conferenceModel, ConferenceDto.class);
    }

    @Override
    public ConferenceDto updateConference(ConferenceDto conferenceDto, Integer userId) {
        ConferenceModel conference = conversionService.convert(conferenceDto, ConferenceModel.class);
        ConferenceModel updatedConference = conferenceService.updateConference(conference, userId);
        return conversionService.convert(updatedConference, ConferenceDto.class);
    }

}
