package com.ctv.conference.core;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.config.CoreTestConfig;
import com.ctv.conference.core.model.ConferenceModel;
import com.ctv.test.Description;
import com.ctv.test.SpringJUnit4CustomRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * @author Dmitry Kovalchuk
 */
@RunWith(SpringJUnit4CustomRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
public class ConferenceServiceImplTest {

    public static final int USER_ID = 1;
    public static final int CONFERENCE_ID = 1;
    public ConferenceModel conferenceModel = new ConferenceModel();
    public static final ConferenceModel NON_FOUNDED_CONFERENCE = null;
    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ConferencePersistenceAdapter persistenceAdapter;

    @Test
    @Description("create conference")
    public void createConference() {
        when(persistenceAdapter.createConference(conferenceModel)).thenReturn(conferenceModel);

        conferenceService.createConference(conferenceModel, USER_ID);

        verify(persistenceAdapter).createConference(conferenceModel);
        assertThat(conferenceModel.getUserId()).isEqualTo(USER_ID);
    }

    @Test
    @Description("delete conference when conference is owned by user")
    public void deleteConferenceOwnedByUser() {
        conferenceService.archiveConference(CONFERENCE_ID, USER_ID);

        verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID);
        verify(persistenceAdapter).archiveConference(CONFERENCE_ID);
    }

    @Test
    @Description("delete conference when conference is not owned by user")
    public void deleteConferenceNotOwnedByUser() {
        doThrow(PermissionDeniedException.class).when(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID);

        try {
            conferenceService.archiveConference(CONFERENCE_ID, USER_ID);
            fail(AccessDeniedException.class.getName() + " has not been thrown");
        } catch (AccessDeniedException ignored) {}

        verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID);
        verify(persistenceAdapter, never()).archiveConference(CONFERENCE_ID);
    }

    @Test
    @Description("find conference by id when conference exists")
    public void findConferenceWhenConferenceExists() {
        when(persistenceAdapter.findConference(CONFERENCE_ID)).thenReturn(conferenceModel);

        conferenceService.findConference(CONFERENCE_ID);

        verify(persistenceAdapter).findConference(CONFERENCE_ID);
    }

    @Test(expected = ResourceNotFoundException.class)
    @Description("find conference by id when conference does not exist")
    public void findConferenceWhenConferenceDoesNotExists() {
        when(persistenceAdapter.findConference(CONFERENCE_ID)).thenReturn(NON_FOUNDED_CONFERENCE);

        conferenceService.findConference(CONFERENCE_ID);
    }

    @Test(expected = DataConflictExceptions.class)
    @Description("update conference when conference id is null")
    public void updateConferenceWhenConferenceIdIsNull() {
        conferenceModel.setId(null);

        conferenceService.updateConference(conferenceModel, USER_ID);
    }

    @Test
    @Description("update conference when conference data are valid")
    public void updateConferenceWhenItsDataAreValid() {
        conferenceModel.setId(CONFERENCE_ID);

        conferenceService.updateConference(conferenceModel, USER_ID);

        verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID);
        verify(persistenceAdapter).updateConference(conferenceModel);
    }

    @Test
    @Description("update conference when it is not owned by user")
    public void updateConferenceWhenNotOwnedByUser() {
        conferenceModel.setId(CONFERENCE_ID);
        doThrow(PermissionDeniedException.class).when(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID);

        try {
            conferenceService.updateConference(conferenceModel, USER_ID);
            fail(AccessDeniedException.class.getName() + " has not been thrown");
        } catch (AccessDeniedException ex) {}

        verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID);
        verify(persistenceAdapter, never()).updateConference(conferenceModel);
    }

    @Test
    @Description("update conference when it is owned by user")
    public void updateConferenceWhenOwnedByUser() {

    }



}
