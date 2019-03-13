package com.packtpub.eventsourcing.commands.persistence;

import com.packtpub.eventsourcing.commands.domain.CommandMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandRepository extends MongoRepository<CommandMetadata, String> {
}
