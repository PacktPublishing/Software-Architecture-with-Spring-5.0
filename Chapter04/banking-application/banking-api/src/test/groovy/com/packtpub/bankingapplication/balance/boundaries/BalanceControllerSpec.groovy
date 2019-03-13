package com.packtpub.bankingapplication.balance.boundaries

import com.packtpub.bankingapplication.balance.domain.Balance
import com.packtpub.bankingapplication.balance.domain.Customer
import com.packtpub.bankingapplication.balance.service.BalanceService
import com.packtpub.bankingapplication.security.domain.JwtUser
import org.springframework.http.HttpStatus
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class BalanceControllerSpec extends Specification {

    def "when the balance is not found a NOT_FOUND status is expected"() {
        given:
        def balanceService = Mock(BalanceService)
        def username = "foo"
        def jwtUser = new JwtUser(username: username)
        def request = Mock(HttpServletRequest)
        request.getAttribute("jwtUser") >> jwtUser
        balanceService.getCurrentBalance(username) >> Optional.empty()
        def balanceController = new BalanceController(balanceService)

        when:
        def response = balanceController.getBalance(request)

        then:
        response.statusCode == HttpStatus.NOT_FOUND
    }

    def "when the balance is found an OK status is expected with the balance information as payload"() {
        given:
        def balanceService = Mock(BalanceService)
        def username = "foo"
        def jwtUser = new JwtUser(username: username)
        def request = Mock(HttpServletRequest)
        request.getAttribute("jwtUser") >> jwtUser
        def balance = new Balance(balance: 1, customer: new Customer(username: username))
        balanceService.getCurrentBalance(username) >> Optional.of(balance)
        def balanceController = new BalanceController(balanceService)

        when:
        def response = balanceController.getBalance(request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.balance == balance.balance
        response.body.customer == balance.customer.username
    }
}
