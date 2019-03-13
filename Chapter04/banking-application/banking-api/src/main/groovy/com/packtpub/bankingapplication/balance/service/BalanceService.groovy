package com.packtpub.bankingapplication.balance.service

import com.packtpub.bankingapplication.balance.persistence.CustomerRepository
import com.packtpub.bankingapplication.balance.domain.Balance
import com.packtpub.bankingapplication.balance.persistence.BalanceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BalanceService {

    private final CustomerRepository customerRepository
    private final BalanceRepository balanceRepository

    @Autowired
    BalanceService(CustomerRepository customerRepository, BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository
        this.customerRepository = customerRepository
    }

    Optional<Balance> getCurrentBalance(String username) {
        def customer = customerRepository.findByUsername(username)
        if (customer.present) {
            return balanceRepository.findByCustomer(customer.get())
        }
        return Optional.empty()
    }
}
