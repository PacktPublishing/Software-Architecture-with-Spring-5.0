package com.packtpub.bankingapplication.accounts.services;

import com.packtpub.bankingapplication.accounts.dao.BankStatementRepository;
import com.packtpub.bankingapplication.accounts.dao.CustomerRepository;
import com.packtpub.bankingapplication.accounts.domain.BankStatement;
import com.packtpub.bankingapplication.accounts.domain.Customer;
import com.packtpub.bankingapplication.notifications.domain.NotificationType;
import com.packtpub.bankingapplication.notifications.factory.NotificationChannelFactory;

import java.util.List;

public class BankStatementService {

    private NotificationChannelFactory notificationChannelFactory;
    private CustomerRepository customerRepository;
    private BankStatementRepository bankStatementRepository;


    public BankStatementService(NotificationChannelFactory notificationChannelFactory, CustomerRepository customerRepository, BankStatementRepository bankStatementRepository) {
        this.notificationChannelFactory = notificationChannelFactory;
        this.customerRepository = customerRepository;
        this.bankStatementRepository = bankStatementRepository;
    }

    public void sendBankStatement(Customer customer) {
        List<NotificationType> preferredChannels = customerRepository.getPreferredNotificationChannels(customer);
        BankStatement bankStatement = bankStatementRepository.getCustomerBankStatement(customer);
        preferredChannels.forEach(
                channel ->
                    notificationChannelFactory.getNotificationChannel(channel).send(bankStatement)
        );
    }
}
