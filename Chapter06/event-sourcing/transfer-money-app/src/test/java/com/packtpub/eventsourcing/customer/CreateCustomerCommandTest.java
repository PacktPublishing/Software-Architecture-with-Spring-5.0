package com.packtpub.eventsourcing.customer;

import com.packtpub.eventsourcing.commands.domain.CommandMetadata;
import com.packtpub.eventsourcing.commands.persistence.CommandRepository;
import com.packtpub.eventsourcing.customer.commands.CreateCustomerCommand;
import com.packtpub.eventsourcing.events.EventProcessor;
import com.packtpub.eventsourcing.events.domain.EventMetadata;
import com.packtpub.eventsourcing.events.persistence.EventRepository;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class CreateCustomerCommandTest {

    @Test
    public void whenExecuteThenProduceTwoEvents() throws Exception {
        JSONObject createCustomerData = buildCreateCustomerData();
        CommandRepository commandRepository = Mockito.mock(CommandRepository.class);
        EventRepository eventRepository = Mockito.mock(EventRepository.class);
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand(createCustomerData, commandRepository, eventRepository, Mockito.mock(EventProcessor.class));

        createCustomerCommand.execute();

        Mockito.verify(eventRepository, Mockito.times(2)).save(Mockito.any(EventMetadata.class));
    }


    @Test
    public void whenTheCommandIsExecutedItIsAlsoPersisted() throws Exception {
        JSONObject createCustomerData = buildCreateCustomerData();
        CommandRepository commandRepository = Mockito.mock(CommandRepository.class);
        EventRepository eventRepository = Mockito.mock(EventRepository.class);
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand(createCustomerData, commandRepository, eventRepository, Mockito.mock(EventProcessor.class));

        createCustomerCommand.execute();

        Mockito.verify(commandRepository, Mockito.times(1)).save(Mockito.any(CommandMetadata.class));
    }

    @Test
    public void whenTheCommandIsExecutedTheEventProcessTheEvents() throws Exception {
        JSONObject createCustomerData = buildCreateCustomerData();
        CommandRepository commandRepository = Mockito.mock(CommandRepository.class);
        EventRepository eventRepository = Mockito.mock(EventRepository.class);
        EventProcessor eventProcessor = Mockito.mock(EventProcessor.class);
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand(createCustomerData, commandRepository, eventRepository, eventProcessor);

        createCustomerCommand.execute();

        Mockito.verify(eventProcessor, Mockito.times(2)).process(Mockito.any(EventMetadata.class));
    }


    private JSONObject buildCreateCustomerData() {
        JSONObject createCustomerData = new JSONObject();
        createCustomerData.put("name", "Rene");
        createCustomerData.put("last_name", "Enriquez");
        createCustomerData.put("account_type", "savings");
        createCustomerData.put("initial_amount", 1000);
        return createCustomerData;
    }
}
