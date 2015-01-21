package com.ctv.conference.core
import com.ctv.conference.core.adapter.ConferencePersistenceAdapter
import com.ctv.conference.core.adapter.MeetupPersistenceAdapter
import com.ctv.conference.core.config.CoreTestConfig
import com.ctv.conference.core.model.Meetup
import com.ctv.test.Spec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import static com.ctv.conference.core.ConferenceErrorCode.ACCESS_TO_CONFERENCE_DENIED
import static com.ctv.conference.core.ConferenceErrorCode.ACCESS_TO_MEETUP_DENIED
import static com.ctv.conference.core.ConferenceErrorCode.MEETUP_ID_NOT_NULL
import static com.ctv.conference.core.ConferenceErrorCode.MEETUP_NOT_FOUND
import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.*
/**
 * @author Dmitry Kovalchuk
 */
@ContextConfiguration(classes = CoreTestConfig)
class MeetupServiceImplTest extends Spec {

    public static final int CONFERENCE_ID = 1
    public static final int USER_ID = 2
    public static final int MEETUP_ID = 3

    @Autowired
    MeetupService meetupService

    @Autowired
    MeetupPersistenceAdapter meetupPersistenceAdapter

    @Autowired
    ConferencePersistenceAdapter conferencePersistenceAdapter

    Meetup meetupIdEqNull = new Meetup()
    Meetup meetupIdNotNull = new Meetup(id: MEETUP_ID)

    def "create meetup when meetup id == null"() {
        given:
        when(conferencePersistenceAdapter.isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(true)

        when:
        meetupService.createMeetup(meetupIdEqNull, CONFERENCE_ID, USER_ID);

        then:
        mockito {
            verify(conferencePersistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID);
        }
    }

    def "create meetup when meetup id != null"() {
        when:
        meetupService.createMeetup(meetupIdNotNull, CONFERENCE_ID, USER_ID)

        then:
        thrown(DataConflictExceptions)
        mockito {
            verifyZeroInteractions(conferencePersistenceAdapter)
        }
    }

    def "create meetup when conference is not owned by user should throw exception"() {
        given:
        when(conferencePersistenceAdapter.isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(false)

        when:
        meetupService.createMeetup(meetupIdEqNull, CONFERENCE_ID, USER_ID)

        then:
        PermissionDeniedException e = thrown()
        verifyErrorMessage(e, ACCESS_TO_CONFERENCE_DENIED)
    }

    def "create meetup when conference is owned by user"() {
        given:
        when(conferencePersistenceAdapter.isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(true)

        when:
        meetupService.createMeetup(meetupIdEqNull, CONFERENCE_ID, USER_ID)

        then:
        mockito {
            verify(conferencePersistenceAdapter).isConferenceOwnedByUser(CONFERENCE_ID, USER_ID)
            verify(meetupPersistenceAdapter).createMeetup(meetupIdEqNull, CONFERENCE_ID)
        }
    }

    def "update meetup when meetup id = null should throw exception"() {
        when:
        meetupService.updateMeetup(meetupIdEqNull, USER_ID)

        then:
        DataConflictExceptions e = thrown()
        verifyErrorMessage(e, MEETUP_ID_NOT_NULL)
    }

    def "update meetup when meetup id != null"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(true)

        when:
        meetupService.updateMeetup(meetupIdNotNull, USER_ID)

        then:
        mockito {
            verify(meetupPersistenceAdapter).isMeetupOwnedByUser(MEETUP_ID, USER_ID)
            verify(meetupPersistenceAdapter).updateMeetup(meetupIdNotNull)
        }
    }

    def "update meetup when user does not have permission for this operation should throw exception"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(false)

        when:
        meetupService.updateMeetup(meetupIdNotNull, USER_ID)

        then:
        PermissionDeniedException e = thrown()
        verifyErrorMessage(e, ACCESS_TO_MEETUP_DENIED)
    }

    def "update meetup when user has permission for this operation"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(true)

        when:
        meetupService.updateMeetup(meetupIdNotNull, USER_ID)

        then:
        mockito {
            verify(meetupPersistenceAdapter).isMeetupOwnedByUser(MEETUP_ID, USER_ID)
            verify(meetupPersistenceAdapter).updateMeetup(meetupIdNotNull)
        }
    }

    def "find all meetups which belong to particular conference"() {
        when:
        meetupService.findMeetupsByConferenceId(CONFERENCE_ID)

        then:
        mockito {
            verify(meetupPersistenceAdapter).findMeetupsByConferenceId(CONFERENCE_ID)
        }
    }

    def "find meet up when meetup exists"() {
        given:
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(meetupIdNotNull)

        when:
        meetupService.findMeetup(MEETUP_ID)

        then:
        noExceptionThrown()
    }

    def "find meet up when meetup does not exist should throw exception"() {
        given:
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(null)

        when:
        meetupService.findMeetup(MEETUP_ID)

        then:
        ResourceNotFoundException e = thrown()
        verifyErrorMessage(e, MEETUP_NOT_FOUND)
    }

    def "hide meetup when user does not have permission should throw exception"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(false)

        when:
        meetupService.hideMeetup(MEETUP_ID, USER_ID)

        then:
        PermissionDeniedException e = thrown()
        verifyErrorMessage(e, ACCESS_TO_MEETUP_DENIED)
    }

    def "hide meetup when user has permission hidden flag should be set to true"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(true)
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(meetupIdNotNull);

        when:
        meetupService.hideMeetup(MEETUP_ID, USER_ID)

        then:
        mockito {
            assertThat(meetupIdNotNull.isHidden()).isTrue()
            verify(meetupPersistenceAdapter).isMeetupOwnedByUser(MEETUP_ID, USER_ID)
            verify(meetupPersistenceAdapter).findMeetup(MEETUP_ID)
            verify(meetupPersistenceAdapter).hideMeetup(meetupIdNotNull)
        }
    }

    def "hide meetup when meetup does not exists should throw exception"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(true)
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(null);

        when:
        meetupService.hideMeetup(MEETUP_ID, USER_ID)

        then:
        ResourceNotFoundException e = thrown()
        verifyErrorMessage(e, MEETUP_NOT_FOUND)
    }

    def "join as a speaker when meetup exists should add user id into speaker list"() {
        given:
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(meetupIdNotNull)

        when:
        meetupService.joinAsSpeaker(MEETUP_ID, USER_ID);

        then:
        mockito {
            assertThat(meetupIdNotNull.getSpeakers()).containsOnly(USER_ID)
            verify(meetupPersistenceAdapter).joinAsSpeaker(meetupIdNotNull)
        }
    }

    def "join as a speaker when meetup does not exist should throw exception"() {
        given:
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(null)

        when:
        meetupService.joinAsSpeaker(MEETUP_ID, USER_ID);

        then:
        ResourceNotFoundException e = thrown()
        verifyErrorMessage(e, MEETUP_NOT_FOUND)
    }

    def "archive meetup when user does not have permission should throw exception"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(CONFERENCE_ID, USER_ID)).thenReturn(false)

        when:
        meetupService.archiveMeetup(MEETUP_ID, USER_ID)

        then:
        PermissionDeniedException e = thrown()
        verifyErrorMessage(e, ACCESS_TO_MEETUP_DENIED)
    }

    def "archive meetup when meetup was not found should throw exception"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(true)
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(null);

        when:
        meetupService.archiveMeetup(MEETUP_ID, USER_ID)

        then:
        ResourceNotFoundException e = thrown()
        verifyErrorMessage(e, MEETUP_NOT_FOUND)
    }

    def "archive meetup when meetup was found deleted flag should be set to true"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(true)
        when(meetupPersistenceAdapter.findMeetup(MEETUP_ID)).thenReturn(meetupIdNotNull);

        when:
        meetupService.archiveMeetup(MEETUP_ID, USER_ID)

        then:
        mockito {
            assertThat(meetupIdNotNull.isDeleted()).isTrue()
            verify(meetupPersistenceAdapter).isMeetupOwnedByUser(MEETUP_ID, USER_ID)
            verify(meetupPersistenceAdapter).archiveMeetup(meetupIdNotNull)
        }
    }

    def "get speakers pool when user has permission to conference"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(true)

        when:
        meetupService.getSpeakerPool(MEETUP_ID, USER_ID)

        then:
        mockito {
            verify(meetupPersistenceAdapter).isMeetupOwnedByUser(MEETUP_ID, USER_ID)
        }
    }

    def "get speakers pool when user does not have permission to conference"() {
        given:
        when(meetupPersistenceAdapter.isMeetupOwnedByUser(MEETUP_ID, USER_ID)).thenReturn(false)

        when:
        meetupService.getSpeakerPool(MEETUP_ID, USER_ID)

        then:
        PermissionDeniedException e = thrown()
        verifyErrorMessage(e, ACCESS_TO_MEETUP_DENIED)
    }
}
