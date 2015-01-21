package com.ctv.conference.persistence;

import com.ctv.conference.persistence.adapter.ConferenceRepository;
import com.ctv.conference.persistence.adapter.dto.ConferenceDto;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static java.util.Objects.nonNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;

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

    @Override
    public boolean isConferenceOwnedByUser(Integer conferenceId, Integer userId) {
        Criteria criteria = where("_id").is(conferenceId).and("userId").is(userId);
        Query query = new Query(criteria);
        ConferenceDto conferenceDto = mongoOps.findOne(query, ConferenceDto.class);
        return nonNull(conferenceDto);
    }

    @Override
    public void archiveConference(Integer conferenceId) {
        Criteria criteria = where("_id").is(conferenceId);
        Update update = new Update();
        update.set("active", false);
        mongoOps.updateFirst(new Query(criteria), update, ConferenceDto.class);
    }

    @Override
    public ConferenceDto findConference(Integer id) {
        Criteria criteria = where("_id").is(id);
        Query query = new Query(criteria);
        return mongoOps.findOne(query, ConferenceDto.class);
    }

    @Override
    public ConferenceDto updateConference(ConferenceDto conference) {
        mongoOps.save(conference);
        return conference;
    }

}
