package com.ctv.conference.persistence;

import com.ctv.conference.persistence.adapter.ConferenceRepository;
import com.ctv.conference.persistence.adapter.dto.ConferenceDto;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

/**
 * @author Dmitry Kovalchuk
 */
@Repository
public class SpringConferenceRepository implements ConferenceRepository {

    private MongoOperations mongoOps;

    public SpringConferenceRepository(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

    @Override
    public ConferenceDto createConference(ConferenceDto conferenceDto) {
        mongoOps.save(conferenceDto);
        return conferenceDto;
    }

}
