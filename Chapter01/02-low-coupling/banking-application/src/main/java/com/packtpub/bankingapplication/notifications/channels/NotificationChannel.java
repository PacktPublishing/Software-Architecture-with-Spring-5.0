package com.packtpub.bankingapplication.notifications.channels;

import com.packtpub.bankingapplication.accounts.domain.BankStatement;

public interface NotificationChannel {

    void send(BankStatement bankStatement);
}
