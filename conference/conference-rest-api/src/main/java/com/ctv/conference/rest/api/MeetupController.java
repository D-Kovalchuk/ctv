package com.ctv.conference.rest.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ctv.conference.rest.api.Endpoint.MEETUP_BY_ID;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Dmitry Kovalchuk
 */
@RestController
public class MeetupController {

    @RequestMapping(value = MEETUP_BY_ID, method = GET)
    public void getAllMeetupsOfConference(@PathVariable Integer confId, @PathVariable Integer meetId) {
        //todo
    }

}
