package com.packtpub.transfermoneyapp.transfer.events;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EventNotificationChannel {

    @Output
    MessageChannel moneyTransferredChannel();
}
