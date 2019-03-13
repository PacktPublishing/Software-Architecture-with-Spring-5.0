package com.packtpub.bankingapplication.balance.persistence

import com.packtpub.bankingapplication.balance.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username)

    Optional<Customer> findByUsernameAndPassword(String username, String password)
}