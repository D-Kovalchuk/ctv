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
public class ConferenceDtoToConferenceModel implements Converter<ConferenceDto, ConferenceModel> {

    @Override
    public ConferenceModel convert(ConferenceDto source) {
        ConferenceModel conference = new ConferenceModel();
        copyProperties(source, conference);
        return conference;
    }
}
