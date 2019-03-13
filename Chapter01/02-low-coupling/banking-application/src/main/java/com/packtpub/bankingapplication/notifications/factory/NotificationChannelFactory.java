package com.packtpub.bankingapplication.notifications.factory;

import com.packtpub.bankingapplication.notifications.domain.NotificationType;
import com.packtpub.bankingapplication.notifications.channels.NotificationChannel;

public interface NotificationChannelFactory {

    NotificationChannel getNotificationChannel(NotificationType type);

}
