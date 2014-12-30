package com.ctv.conference.rest.adapter.converter;

import com.ctv.conference.core.model.ConferenceModel;
import com.ctv.conference.rest.adapter.dto.ConferenceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Dmitry Kovalchuk
 */
@Component
public class ConferenceModelToConferenceDto implements Converter<ConferenceModel, ConferenceDto> {

    @Override
    public ConferenceDto convert(ConferenceModel source) {
        ConferenceDto conferenceDto = new ConferenceDto();
        copyProperties(source, conferenceDto);
        return conferenceDto;
    }

}
