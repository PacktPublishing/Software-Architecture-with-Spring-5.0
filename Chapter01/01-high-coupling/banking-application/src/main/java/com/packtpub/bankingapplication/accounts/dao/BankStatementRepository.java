package com.packtpub.bankingapplication.accounts.dao;

import com.packtpub.bankingapplication.accounts.domain.BankStatement;
import com.packtpub.bankingapplication.accounts.domain.Customer;

public interface BankStatementRepository {

    BankStatement getCustomerBankStatement(Customer customer);
}
