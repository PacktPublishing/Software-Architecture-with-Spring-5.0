package com.packtpub.bankingapplication.balance.service

import com.packtpub.bankingapplication.balance.domain.Customer
import com.packtpub.bankingapplication.balance.persistence.BalanceRepository
import com.packtpub.bankingapplication.balance.persistence.CustomerRepository
import spock.lang.Specification

class BalanceServiceSpec extends Specification {

    def "when the customer is not found the balance is not queried"() {
        given:
        def nonExistentUsername = "foo"
        def customerRepository = Mock(CustomerRepository)
        customerRepository.findByUsername(nonExistentUsername) >> Optional.empty()
        def balanceRepository = Mock(BalanceRepository)
        def balanceService = new BalanceService(customerRepository, balanceRepository)


        when:
        balanceService.getCurrentBalance(nonExistentUsername)


        then:
        0 * balanceRepository.findByCustomer(_ as Customer)
    }

    def "when the customer is found the balance is queried"() {
        given:
        def existingCustomer = "foo"
        def customer = new Customer()
        def customerRepository = Mock(CustomerRepository)
        customerRepository.findByUsername(existingCustomer) >> Optional.of(customer)
        def balanceRepository = Mock(BalanceRepository)
        def balanceService = new BalanceService(customerRepository, balanceRepository)


        when:
        balanceService.getCurrentBalance(existingCustomer)


        then:
        1 * balanceRepository.findByCustomer(customer)
    }
}
