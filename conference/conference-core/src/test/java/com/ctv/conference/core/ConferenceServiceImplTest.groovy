package com.ctv.conference.core
import com.ctv.conference.core.adapter.ConferencePersistenceAdapter
import com.ctv.conference.core.config.CoreTestConfig
import com.ctv.conference.core.model.ConferenceModel
import com.ctv.test.Spec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import static com.ctv.conference.core.ConferenceErrorCode.ACCESS_TO_CONFERENCE_DENIED
import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.*
/**
 * @author Dmitry Kovalchuk
 */
@ContextConfiguration(classes = CoreTestConfig.class)
public class ConferenceServiceImplTest extends Spec {

    static final int USER_ID = 1
    static final int CONFERENCE_ID = 1
    ConferenceModel conferenceModel = new ConferenceModel()
    static final ConferenceModel NON_FOUNDED_CONFERENCE = null

    @Autowired
    ConferenceService conferenceService

    @Autowired
    ConferencePersistenceAdapter persistenceAdapter


    def "create conference"() {
        given:
        when(persistenceAdapter.createConference(conferenceModel)).thenReturn(conferenceModel)

        when:
        conferenceService.createConference(conferenceModel, USER_ID)

        then:
        mockito {
            verify(persistenceAdapter).createConference(conferenceModel)
            assertThat(conferenceModel.getUserId()).isEqualTo(USER_ID)
        }
    }


    def "delete conference when conference is owned by user"() {
        given:
        when(persistenceAdapter.isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(true)

        when:
        conferenceService.archiveConference(CONFERENCE_ID, USER_ID)

        then:
        mockito {
            verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)
            verify(persistenceAdapter).archiveConference(CONFERENCE_ID)
        }
    }


    def "delete conference when conference is not owned by user"() {
        given:
        when(persistenceAdapter.isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(false)

        when:
        conferenceService.archiveConference(CONFERENCE_ID, USER_ID)

        then:
        PermissionDeniedException e = thrown()
        verifyErrorMessage(e, ACCESS_TO_CONFERENCE_DENIED)
        mockito {
            verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)
            verify(persistenceAdapter, never()).archiveConference(CONFERENCE_ID)
        }
    }


    def "find conference by id when conference exists"() {
        given:
        when(persistenceAdapter.findConference(CONFERENCE_ID)).thenReturn(conferenceModel)

        when:
        conferenceService.findConference(CONFERENCE_ID)

        then:
        mockito {
            verify(persistenceAdapter).findConference(CONFERENCE_ID)
        }
    }

    def "find conference by id when conference does not exist"() {
        given:
        when(persistenceAdapter.findConference(CONFERENCE_ID)).thenReturn(NON_FOUNDED_CONFERENCE)

        when:
        conferenceService.findConference(CONFERENCE_ID)

        then:
        thrown(ResourceNotFoundException)
    }

    def "update conference when conference id is null"() {
        given:
        conferenceModel.setId(null)

        when:
        conferenceService.updateConference(conferenceModel, USER_ID)

        then:
        thrown(DataConflictExceptions)
    }


    def "update conference when conference data are valid"() {
        given:
        conferenceModel.setId(CONFERENCE_ID)
        when(persistenceAdapter.isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(true)

        when:
        conferenceService.updateConference(conferenceModel, USER_ID)

        then:
        mockito {
            verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)
            verify(persistenceAdapter).updateConference(conferenceModel)
        }
    }


    def "update conference when it is not owned by user"() {
        given:
        conferenceModel.setId(CONFERENCE_ID)
        when(persistenceAdapter.isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(false)

        when:
        conferenceService.updateConference(conferenceModel, USER_ID)

        then:
        PermissionDeniedException e = thrown()
        verifyErrorMessage(e, ACCESS_TO_CONFERENCE_DENIED)
        mockito {
            verify(persistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)
            verify(persistenceAdapter, never()).updateConference(conferenceModel)
        }
    }

}