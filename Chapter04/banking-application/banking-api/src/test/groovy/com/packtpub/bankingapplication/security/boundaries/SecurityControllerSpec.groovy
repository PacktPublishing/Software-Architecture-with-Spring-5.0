package com.packtpub.bankingapplication.security.boundaries

import com.packtpub.bankingapplication.balance.domain.Credentials
import com.packtpub.bankingapplication.security.service.LoginService
import org.springframework.http.HttpStatus
import spock.lang.Specification

import javax.security.auth.login.LoginException

class SecurityControllerSpec extends Specification {

    def "when the credentials are not found, an UNAUTHORIZED code is returned"() {
        given:
        def nonExistentCredentials = new Credentials(username: "foo", password: "bar")
        def loginService = Mock(LoginService)
        loginService.login(nonExistentCredentials) >> {
            throw new LoginException()
        }
        def securityController = new SecurityController(loginService)

        when:
        def response = securityController.auth(nonExistentCredentials)


        then:
        response.statusCode == HttpStatus.UNAUTHORIZED
    }

    def "when the credentials are valid, an OK code is returned with the token"() {
        given:
        def validCredentials = new Credentials(username: "foo", password: "bar")
        def loginService = Mock(LoginService)
        def jwtToken = "xyz"
        loginService.login(validCredentials) >> jwtToken
        def securityController = new SecurityController(loginService)

        when:
        def response = securityController.auth(validCredentials)

        then:
        response.statusCode == HttpStatus.OK
        response.body == jwtToken
    }
}
