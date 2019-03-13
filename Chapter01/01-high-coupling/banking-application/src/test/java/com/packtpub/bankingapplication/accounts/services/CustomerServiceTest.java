package com.packtpub.bankingapplication.accounts.services;

import com.packtpub.bankingapplication.accounts.dao.CustomerRepository;
import com.packtpub.bankingapplication.accounts.domain.Customer;
import com.packtpub.bankingapplication.notifications.domain.NotificationChannel;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceTest {

    @Test
    public void theNotificationChannelsAreSavedByTheDataRepository() throws Exception {
        NotificationChannel channelOne = Mockito.mock(NotificationChannel.class);
        NotificationChannel channelTwo = Mockito.mock(NotificationChannel.class);
        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
        CustomerService customerService = new CustomerService(customerRepository);
        List<NotificationChannel> notificationChannels = new ArrayList();
        notificationChannels.add(channelOne);
        notificationChannels.add(channelTwo);
        Customer customer = Mockito.mock(Customer.class);

        customerService.savePreferredNotificationChannels(customer, notificationChannels);

        Mockito.verify(customerRepository, Mockito.times(1)).savePreferredChannel(customer, channelOne);
        Mockito.verify(customerRepository, Mockito.times(1)).savePreferredChannel(customer, channelTwo);
    }
}
