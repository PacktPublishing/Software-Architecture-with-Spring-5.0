package com.packtpub.bankingapplication.accounts.dao;

import com.packtpub.bankingapplication.accounts.domain.Customer;
import com.packtpub.bankingapplication.notifications.domain.NotificationChannel;

import java.util.List;

public interface CustomerRepository {

    void savePreferredChannel(Customer customer, NotificationChannel notificationChannel);

    List<NotificationChannel> getPreferredNotificationChannels(Customer customer);
}
