package com.ctv.conference.rest.api;

import com.ctv.conference.core.model.Talk;
import com.ctv.shared.model.CtvUserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ctv.conference.rest.api.Endpoint.*;
import static com.ctv.conference.rest.api.Endpoint.X_AUTH_TOKEN;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Dmitry Kovalchuk
 */
@RestController
public class TalkController {

    @RequestMapping(value = MEETUP_BY_ID_TALK, method = POST, headers = X_AUTH_TOKEN)
    public Talk createTalk(@PathVariable Integer meetId, @RequestBody Talk talk, @AuthenticationPrincipal CtvUserDetails userDetails) {
        return new Talk();
    }

    @RequestMapping(value = TALK_BY_ID, method = GET)
    public Talk getTalk(@PathVariable Integer id) {
        return new Talk();
    }

    @RequestMapping(value = TALK, method = GET)
    public List<Talk> getTalks() {
        return asList(new Talk());
    }

    @RequestMapping(value = TALK, method = PUT, headers = X_AUTH_TOKEN)
    public Talk updateTalk(@RequestBody Talk talk) {
        return new Talk();
    }

    @RequestMapping(value = TALK_BY_ID, method = PUT, headers = X_AUTH_TOKEN)
    public Talk hideTalk(@RequestBody Talk talk) {
        //todo provide this style of update {visible: false}
        return new Talk();
    }

    @RequestMapping(value = TALK_BY_ID, method = DELETE, headers = X_AUTH_TOKEN)
    public void archiveTalk(@PathVariable Integer id) {

    }

    @RequestMapping(value = ASSIGN_SPEAKER_TO_TALK, method = POST, headers = X_AUTH_TOKEN)
    public void assignSpeaker(@PathVariable Integer talkId, @PathVariable Integer userId) {

    }

}
