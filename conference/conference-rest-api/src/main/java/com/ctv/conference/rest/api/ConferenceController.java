package com.ctv.conference.rest.api;

import com.ctv.conference.rest.adapter.ConferenceRestAdapter;
import com.ctv.conference.rest.adapter.dto.ConferenceDto;
import com.ctv.shared.model.CtvUserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.ctv.conference.rest.api.Endpoint.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Dmitry Kovalchuk
 */
@RestController
@RequestMapping(CONFERENCE)
public class ConferenceController {

    private ConferenceRestAdapter restAdapter;

    public ConferenceController(ConferenceRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    //todo sort by...
    @RequestMapping(params = {PAGE_PARAM, SIZE_PARAM}, method = GET)
    public void createConference(@RequestParam(defaultValue = START_PAGE) int page,
                                 @RequestParam(defaultValue = PAGE_SIZE) int size) {

    }

    @RequestMapping(value = "{confId}/meetup/" + "{meetId}", method = GET)
    public void getAllMeetupsOfConference(@PathVariable Integer confId, @PathVariable Integer meetId) {

    }


    @RequestMapping(value = "/{id}", method = GET)
    public void getConference(@PathVariable Integer id) {

    }

    @RequestMapping(method = POST, headers = X_AUTH_TOKEN)
    public ConferenceDto createConference(ConferenceDto conferenceDto, @AuthenticationPrincipal CtvUserDetails userDetails) {
        Integer userId = userDetails.getId();
        conferenceDto.setId(userId);
        return restAdapter.createConference(conferenceDto);
    }

    @RequestMapping(value = "/{id}", method = PUT, headers = X_AUTH_TOKEN)
    public void updateConference(@PathVariable Integer id, @AuthenticationPrincipal CtvUserDetails userDetails) {

    }

    @RequestMapping(value = "/{id}", method = DELETE, headers = X_AUTH_TOKEN)
    public void archiveConference(@PathVariable Integer id, @AuthenticationPrincipal CtvUserDetails userDetails) {
        restAdapter.archiveConference(id, userDetails.getId());
    }

}
