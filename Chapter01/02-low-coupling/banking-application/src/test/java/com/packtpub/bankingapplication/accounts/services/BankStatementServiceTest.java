package com.packtpub.bankingapplication.accounts.services;


import com.packtpub.bankingapplication.accounts.dao.BankStatementRepository;
import com.packtpub.bankingapplication.accounts.dao.CustomerRepository;
import com.packtpub.bankingapplication.accounts.domain.BankStatement;
import com.packtpub.bankingapplication.accounts.domain.Customer;
import com.packtpub.bankingapplication.notifications.domain.NotificationType;
import com.packtpub.bankingapplication.notifications.factory.NotificationChannelFactory;
import com.packtpub.bankingapplication.notifications.channels.impl.EmailNotificationChannel;
import com.packtpub.bankingapplication.notifications.channels.impl.FaxNotificationChannel;
import com.packtpub.bankingapplication.notifications.channels.NotificationChannel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BankStatementServiceTest {

    NotificationChannel emailChannel, faxChannel;
    NotificationChannelFactory notificationChannelsFactory;

    @Before
    public void initialize(){
        emailChannel = mock(EmailNotificationChannel.class);
        faxChannel = mock(FaxNotificationChannel.class);
        notificationChannelsFactory = buildNotificationChannelFactory(emailChannel, faxChannel);
    }

    @Test
    public void theBankStatementIsSendUsingThePreferredNotificationChannels() throws Exception {

        BankStatement bankStatement = mock(BankStatement.class);
        Customer customer = mock(Customer.class);
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        BankStatementRepository bankStatementRepository = mock(BankStatementRepository.class);
        when(customerRepository.getPreferredNotificationChannels(customer)).thenReturn(buildListOfNotificationTypes());
        when(bankStatementRepository.getCustomerBankStatement(customer)).thenReturn(bankStatement);
        BankStatementService bankStatementService = new BankStatementService(notificationChannelsFactory, customerRepository, bankStatementRepository);

        bankStatementService.sendBankStatement(customer);

        verify(customerRepository, times(1)).getPreferredNotificationChannels(customer);
        verify(emailChannel, times(1)).send(bankStatement);
        verify(faxChannel, times(1)).send(bankStatement);

    }

    private NotificationChannelFactory buildNotificationChannelFactory(NotificationChannel emailChannel, NotificationChannel faxChannel) {
        NotificationChannelFactory notificationChannelsFactory = mock(NotificationChannelFactory.class);
        when(notificationChannelsFactory.getNotificationChannel(NotificationType.EMAIL)).thenReturn(emailChannel);
        when(notificationChannelsFactory.getNotificationChannel(NotificationType.FAX)).thenReturn(faxChannel);
        return notificationChannelsFactory;
    }

    private List<NotificationType> buildListOfNotificationTypes() {
        List<NotificationType> preferredNotificationChannels = new ArrayList<>();
        preferredNotificationChannels.add(NotificationType.EMAIL);
        preferredNotificationChannels.add(NotificationType.FAX);
        return preferredNotificationChannels;
    }
}
