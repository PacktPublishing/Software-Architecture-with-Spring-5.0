package com.packtpub.bankingapplication.accounts.dao;

import com.packtpub.bankingapplication.accounts.domain.Customer;
import com.packtpub.bankingapplication.notifications.domain.NotificationType;

import java.util.List;

public interface CustomerRepository {

    void savePreferredChannel(Customer customer, NotificationType notificationChannel);

    List<NotificationType> getPreferredNotificationChannels(Customer customer);
}
