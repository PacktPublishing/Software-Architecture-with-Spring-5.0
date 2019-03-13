package com.packtpub.bankingapplication.balance.domain;

public class BalanceInformation {

    private int balance;
    private String customer;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
