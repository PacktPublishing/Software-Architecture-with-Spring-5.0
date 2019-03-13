package com.packtpub.eventsourcing.events.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document
public class EventMetadata {

    @Id
    protected String eventId;
    protected JSONObject eventData;
    protected String commandId;
    protected String eventName;
    private long timestamp;

    public EventMetadata(JSONObject eventData, String commandId, String eventId, String eventName) {
        this.eventName = eventName;
        this.eventData = eventData;
        this.commandId = commandId;
        this.eventId = eventId;
        this.timestamp = System.currentTimeMillis();
    }

}
