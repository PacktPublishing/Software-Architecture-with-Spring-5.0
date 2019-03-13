package com.packtpub.bankingapplication.accounts.services;


import com.packtpub.bankingapplication.accounts.dao.BankStatementRepository;
import com.packtpub.bankingapplication.accounts.dao.CustomerRepository;
import com.packtpub.bankingapplication.accounts.domain.BankStatement;
import com.packtpub.bankingapplication.accounts.domain.Customer;
import com.packtpub.bankingapplication.notifications.domain.NotificationChannel;
import com.packtpub.bankingapplication.notifications.services.NotificationService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BankStatementServiceTest {

    @Test
    public void theBankStatementIsSendUsingThePreferredNotificationChannels() throws Exception {
        NotificationService notificationService = mock(NotificationService.class);
        BankStatement bankStatement = mock(BankStatement.class);
        Customer customer = Mockito.mock(Customer.class);
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        List<NotificationChannel> preferredNotificationChannels = new ArrayList<>();
        NotificationChannel emailChannel = mock(NotificationChannel.class);
        NotificationChannel faxChannel = mock(NotificationChannel.class);
        when(emailChannel.getChannelName()).thenReturn("email");
        when(faxChannel.getChannelName()).thenReturn("fax");
        preferredNotificationChannels.add(emailChannel);
        preferredNotificationChannels.add(faxChannel);
        when(customerRepository.getPreferredNotificationChannels(customer)).thenReturn(preferredNotificationChannels);
        BankStatementRepository bankStatementRepository = mock(BankStatementRepository.class);
        when(bankStatementRepository.getCustomerBankStatement(customer)).thenReturn(bankStatement);
        BankStatementService bankStatementService = new BankStatementService(notificationService, customerRepository, bankStatementRepository);

        bankStatementService.sendBankStatement(customer);

        verify(customerRepository, times(1)).getPreferredNotificationChannels(customer);
        verify(notificationService, times(1)).sendByEmail(bankStatement);
        verify(notificationService, times(1)).sendByFax(bankStatement);

    }
}
