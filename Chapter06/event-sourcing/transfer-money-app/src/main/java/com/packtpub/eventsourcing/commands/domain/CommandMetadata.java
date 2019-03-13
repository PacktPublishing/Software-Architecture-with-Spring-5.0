package com.packtpub.eventsourcing.commands.domain;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class CommandMetadata {

    @Id
    private String commandId;
    private String commandName;
    private JSONObject commandData;
    private long timestamp;

    public CommandMetadata(String commandId, String commandName, JSONObject commandData) {
        this.commandId = commandId;
        this.commandName = commandName;
        this.commandData = commandData;
        this.timestamp = System.currentTimeMillis();
    }

}
