package com.packtpub.bankingapplication.notifications.services;

import com.packtpub.bankingapplication.accounts.domain.BankStatement;

public interface NotificationService {

    void sendByEmail(BankStatement bankStatement);

    void sendByFax(BankStatement bankStatement);
}
