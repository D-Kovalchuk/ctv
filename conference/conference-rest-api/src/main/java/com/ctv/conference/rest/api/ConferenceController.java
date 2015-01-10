package com.ctv.conference.rest.api;

import com.ctv.conference.rest.adapter.ConferenceRestAdapter;
import com.ctv.conference.rest.adapter.dto.ConferenceDto;
import com.ctv.shared.model.CtvUserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = BY_ID, method = GET)
    public ConferenceDto getConference(@PathVariable Integer id) {
        return restAdapter.findConference(id);
    }

    @RequestMapping(method = POST, headers = X_AUTH_TOKEN)
    public ConferenceDto createConference(@RequestBody ConferenceDto conferenceDto, @AuthenticationPrincipal CtvUserDetails userDetails) {
        return restAdapter.createConference(conferenceDto, userDetails.getId());
    }

    @RequestMapping(method = PUT, headers = X_AUTH_TOKEN)
    public ConferenceDto updateConference(@RequestBody ConferenceDto conference, @AuthenticationPrincipal CtvUserDetails userDetails) {
        return restAdapter.updateConference(conference, userDetails.getId());
    }

    @RequestMapping(value = BY_ID, method = DELETE, headers = X_AUTH_TOKEN)
    public void archiveConference(@PathVariable Integer id, @AuthenticationPrincipal CtvUserDetails userDetails) {
        restAdapter.archiveConference(id, userDetails.getId());
    }

}
