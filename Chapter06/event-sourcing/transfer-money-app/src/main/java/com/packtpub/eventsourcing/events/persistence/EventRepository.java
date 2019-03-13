package com.packtpub.eventsourcing.events.persistence;

import com.packtpub.eventsourcing.events.domain.EventMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventMetadata, String> {
}
