package com.packtpub.notifications.transfer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EventNotificationChannel {

    @Input
    SubscribableChannel subscriptionOnMoneyTransferredChannel();
}
