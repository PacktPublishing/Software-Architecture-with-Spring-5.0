package com.packtpub.bankingapplication.balance.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Customer {

    @Id
    @GeneratedValue
    Long id

    String username, password

}
