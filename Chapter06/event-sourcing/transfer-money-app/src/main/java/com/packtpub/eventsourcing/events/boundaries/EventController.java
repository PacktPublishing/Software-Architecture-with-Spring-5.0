package com.packtpub.eventsourcing.events.boundaries;

import com.packtpub.eventsourcing.events.persistence.EventRepository;
import com.packtpub.eventsourcing.events.domain.EventMetadata;
import com.packtpub.eventsourcing.events.EventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventProcessor eventProcessor;

    @PostMapping("/events/{id}")
    public void createCustomer(@PathVariable("id") String id) throws Exception {
        EventMetadata event = eventRepository.findById(id).get();
        eventProcessor.process(event);
    }
}
