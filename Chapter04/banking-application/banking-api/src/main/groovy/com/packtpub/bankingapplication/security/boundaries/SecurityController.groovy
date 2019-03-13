package com.packtpub.bankingapplication.security.boundaries

import com.packtpub.bankingapplication.balance.domain.Credentials
import com.packtpub.bankingapplication.security.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController {

    private final LoginService loginService

    @Autowired
    SecurityController(LoginService loginService) {
        this.loginService = loginService
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/public/auth")
    public ResponseEntity<String> auth(@RequestBody Credentials credentials) {
        try {
            String token = loginService.login(credentials)
            return ResponseEntity.ok(token)
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED)
        }
    }
}

