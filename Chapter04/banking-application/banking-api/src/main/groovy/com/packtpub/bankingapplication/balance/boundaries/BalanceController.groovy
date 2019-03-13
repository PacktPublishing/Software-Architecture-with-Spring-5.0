package com.packtpub.bankingapplication.balance.boundaries

import com.packtpub.bankingapplication.balance.domain.BalanceInformation
import com.packtpub.bankingapplication.balance.service.BalanceService
import com.packtpub.bankingapplication.security.domain.JwtUser
import com.packtpub.bankingapplication.balance.domain.Balance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@RestController
class BalanceController {

    private final BalanceService balanceService

    @Autowired
    BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/secure/balance")
    ResponseEntity<BalanceInformation> getBalance(HttpServletRequest request) {
        JwtUser user = request.getAttribute("jwtUser")
        Optional<Balance> balance = balanceService.getCurrentBalance(user.username)
        if (balance.present) {
            def balanceFound = balance.get()
            BalanceInformation information = new BalanceInformation(balance: balanceFound.balance, customer: balanceFound.customer.username)
            return new ResponseEntity<Balance>(information, HttpStatus.OK)
        }
        return new ResponseEntity<Balance>(body: null, HttpStatus.NOT_FOUND)
    }
}
