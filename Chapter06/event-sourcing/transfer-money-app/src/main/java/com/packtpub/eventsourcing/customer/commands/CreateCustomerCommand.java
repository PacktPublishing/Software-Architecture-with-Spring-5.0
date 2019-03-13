package com.packtpub.eventsourcing.customer.commands;

import com.packtpub.eventsourcing.commands.Command;
import com.packtpub.eventsourcing.commands.domain.CommandMetadata;
import com.packtpub.eventsourcing.commands.persistence.CommandRepository;
import com.packtpub.eventsourcing.events.domain.EventMetadata;
import com.packtpub.eventsourcing.events.EventProcessor;
import com.packtpub.eventsourcing.events.persistence.EventRepository;
import org.json.simple.JSONObject;

import java.util.UUID;

public class CreateCustomerCommand extends Command {

    public CreateCustomerCommand(JSONObject data, CommandRepository commandRepository, EventRepository eventRepository, EventProcessor eventProcessor) {
        super(data, commandRepository, eventRepository, eventProcessor);
    }

    @Override
    public void execute() {

        String commandId = UUID.randomUUID().toString();
        CommandMetadata commandMetadata = new CommandMetadata(commandId, getName(), this.data);
        commandRepository.save(commandMetadata);

        String customerUuid = UUID.randomUUID().toString();

        JSONObject customerInformation = getCustomerInformation();
        customerInformation.put("customer_id", customerUuid);
        EventMetadata customerCreatedEvent = new EventMetadata(customerInformation, commandId, UUID.randomUUID().toString(), "CustomerCreated");
        eventRepository.save(customerCreatedEvent);
        eventProcessor.process(customerCreatedEvent);

        JSONObject accountInformation = getAccountInformation();
        accountInformation.put("customer_id", customerUuid);
        EventMetadata accountCreatedEvent = new EventMetadata(accountInformation, commandId, UUID.randomUUID().toString(), "AccountCreated");
        eventRepository.save(accountCreatedEvent);
        eventProcessor.process(accountCreatedEvent);

    }

    private JSONObject getAccountInformation() {
        JSONObject accountData = new JSONObject();
        accountData.put("balance", this.data.get("initial_amount"));
        accountData.put("account_type", this.data.get("account_type"));
        accountData.put("account_id", UUID.randomUUID().toString());
        return accountData;
    }

    private JSONObject getCustomerInformation() {
        JSONObject customerData = new JSONObject();
        customerData.put("name", this.data.get("name"));
        customerData.put("last_name", this.data.get("last_name"));
        return customerData;
    }

    @Override
    public String getName() {
        return "CreateCustomer";
    }
}
