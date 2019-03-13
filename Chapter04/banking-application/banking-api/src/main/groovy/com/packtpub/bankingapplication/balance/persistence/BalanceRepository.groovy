package com.packtpub.bankingapplication.balance.persistence

import com.packtpub.bankingapplication.balance.domain.Balance
import com.packtpub.bankingapplication.balance.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByCustomer(Customer customer)

}