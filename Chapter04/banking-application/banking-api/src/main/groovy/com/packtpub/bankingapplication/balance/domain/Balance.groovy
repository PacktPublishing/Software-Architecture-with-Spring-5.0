package com.packtpub.bankingapplication.balance.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Balance {


    @Id
    @GeneratedValue
    long id

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer

    int balance

}

