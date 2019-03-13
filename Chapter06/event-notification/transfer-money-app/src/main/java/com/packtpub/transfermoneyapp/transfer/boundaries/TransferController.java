package com.packtpub.transfermoneyapp.transfer.boundaries;

import com.packtpub.transfermoneyapp.transfer.domain.TransferMoneyDetails;
import com.packtpub.transfermoneyapp.transfer.events.EventNotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TransferController {

    private final MessageChannel moneyTransferredChannel;

    public TransferController(EventNotificationChannel channel) {
        this.moneyTransferredChannel = channel.moneyTransferredChannel();
    }

    @PostMapping("/transfer")
    public void doTransfer(@RequestBody TransferMoneyDetails transferMoneyDetails) {
        log.info("Transferring money with details: " + transferMoneyDetails);
        Message<String> moneyTransferredEvent = MessageBuilder.withPayload("Money transferred for client with id: " + transferMoneyDetails.getCustomerId()).build();
        this.moneyTransferredChannel.send(moneyTransferredEvent);
    }
}
