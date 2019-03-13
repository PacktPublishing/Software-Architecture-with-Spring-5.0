package com.packtpub.eventsourcing.customer.boundaries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packtpub.eventsourcing.commands.Command;
import com.packtpub.eventsourcing.commands.CommandInvoker;
import com.packtpub.eventsourcing.commands.persistence.CommandRepository;
import com.packtpub.eventsourcing.customer.commands.CreateCustomerCommand;
import com.packtpub.eventsourcing.customer.domain.CustomerData;
import com.packtpub.eventsourcing.events.EventProcessor;
import com.packtpub.eventsourcing.events.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class CustomerController {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private EventProcessor eventProcessor;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/customer")
    public void createCustomer(@RequestBody CustomerData customerData) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(customerData,
                new TypeReference<Map<String, Object>>() {
                }
        );

        JSONObject customerDataAsJson = new JSONObject(map);

        Command createCustomerCommand = new CreateCustomerCommand(customerDataAsJson, commandRepository, eventRepository, eventProcessor);
        CommandInvoker commandInvoker = new CommandInvoker();
        commandInvoker.invoke(createCustomerCommand);

        log.info("COMMAND INFORMATION");
        commandRepository.findAll().stream().forEach(command -> {
            log.info("id: {} , name: {} , data: {} ", command.getCommandId(), command.getCommandName(), command.getCommandData().toJSONString());
        });

        log.info("EVENT INFORMATION");
        eventRepository.findAll().stream().forEach(event -> {
            log.info("id: {} , name: {} , command id: {} , data: {} ", event.getEventId(), event.getEventName(), event.getCommandId(), event.getEventData().toJSONString());
        });
    }
}
