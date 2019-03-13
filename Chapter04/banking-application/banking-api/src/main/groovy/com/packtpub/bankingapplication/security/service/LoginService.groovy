package com.packtpub.bankingapplication.security.service

import com.packtpub.bankingapplication.balance.domain.Credentials
import com.packtpub.bankingapplication.balance.domain.Customer
import com.packtpub.bankingapplication.balance.persistence.CustomerRepository
import com.packtpub.bankingapplication.security.domain.JwtUser
import org.springframework.stereotype.Service

import javax.security.auth.login.LoginException

@Service
class LoginService {

    private final CustomerRepository customerRepository
    private final JwtService jwtService

    LoginService(CustomerRepository customerRepository, JwtService jwtService) {
        this.jwtService = jwtService
        this.customerRepository = customerRepository
    }

    String login(Credentials credentials) {
        Optional<Customer> customer = customerRepository.findByUsernameAndPassword(credentials.username, credentials.password)
        if (customer.present) {
            return jwtService.getToken(new JwtUser(username: credentials.username, role: "CUSTOMER"))
        }
        throw new LoginException("Invalid credentials provided")
    }
}
