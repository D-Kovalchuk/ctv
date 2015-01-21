package com.ctv.conference.rest.api;

import com.ctv.conference.core.model.Meetup;
import com.ctv.shared.model.CtvUserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ctv.conference.rest.api.Endpoint.*;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Dmitry Kovalchuk
 */
@RestController
public class MeetupController {

    @RequestMapping(value = MEETUP_BY_CONFERENCE_ID, method = GET)
    public List<Meetup> getAllMeetupsOfConference(@PathVariable Integer confId) {
        //todo  1: meetup object must contain only necessary information
        return asList(new Meetup());
    }

    @RequestMapping(value = MEETUP_BY_ID, method = GET)
    public Meetup getMeetup(@PathVariable Integer meetId) {
        //todo 1: meetup must contain all of the information
        return new Meetup();
    }

    @RequestMapping(value = MEETUP_BY_CONFERENCE_ID, method = POST, headers = X_AUTH_TOKEN)
    public Meetup createMeetup(@PathVariable Integer confId, @RequestBody Meetup meetup, @AuthenticationPrincipal CtvUserDetails userDetails) {
        return new Meetup();
    }

    @RequestMapping(value = MEETUP, method = PUT, headers = X_AUTH_TOKEN)
    public Meetup updateMeetup(@RequestBody Meetup meetup, @AuthenticationPrincipal CtvUserDetails userDetails) {
        return new Meetup();
    }

    @RequestMapping(value = MEETUP_BY_ID, method = PUT, headers = X_AUTH_TOKEN)
    public void hideMeetup(@PathVariable Integer meetupId, @RequestBody Meetup meetup, @AuthenticationPrincipal CtvUserDetails userDetails) {

    }

    @RequestMapping(value = MEETUP_BY_ID, method = PUT, headers = X_AUTH_TOKEN)
    public void joinAsSpeaker(@PathVariable Integer id, @AuthenticationPrincipal CtvUserDetails userDetails) {
        Integer userId = userDetails.getId();
    }

    @RequestMapping(value = MEETUP_BY_ID_SPEAKER, method = PUT, headers = X_AUTH_TOKEN)
    public void getSpeakerPool(@PathVariable Integer id, @AuthenticationPrincipal CtvUserDetails userDetails) {
        Integer userId = userDetails.getId();
    }

    @RequestMapping(value = MEETUP_BY_ID, method = DELETE, headers = X_AUTH_TOKEN)
    public void archiveMeetup(@PathVariable Integer id) {

    }
}
