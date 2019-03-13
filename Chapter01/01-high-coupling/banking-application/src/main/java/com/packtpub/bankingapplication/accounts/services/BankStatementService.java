package com.packtpub.bankingapplication.accounts.services;

import com.packtpub.bankingapplication.accounts.dao.BankStatementRepository;
import com.packtpub.bankingapplication.accounts.dao.CustomerRepository;
import com.packtpub.bankingapplication.accounts.domain.BankStatement;
import com.packtpub.bankingapplication.accounts.domain.Customer;
import com.packtpub.bankingapplication.notifications.domain.NotificationChannel;
import com.packtpub.bankingapplication.notifications.services.NotificationService;

import java.util.List;

public class BankStatementService {

    private NotificationService notificationService;
    private CustomerRepository customerRepository;
    private BankStatementRepository bankStatementRepository;


    public BankStatementService(NotificationService notificationService, CustomerRepository customerRepository, BankStatementRepository bankStatementRepository) {
        this.notificationService = notificationService;
        this.customerRepository = customerRepository;
        this.bankStatementRepository = bankStatementRepository;
    }

    public void sendBankStatement(Customer customer) {
        List<NotificationChannel> preferredChannels = customerRepository.getPreferredNotificationChannels(customer);
        BankStatement bankStatement = bankStatementRepository.getCustomerBankStatement(customer);
        preferredChannels.forEach(
                channel -> {
                    if ("email".equals(channel.getChannelName())) {
                        notificationService.sendByEmail(bankStatement);
                    } else if ("fax".equals(channel.getChannelName())) {
                        notificationService.sendByFax(bankStatement);
                    }
                }
        );
    }
}
