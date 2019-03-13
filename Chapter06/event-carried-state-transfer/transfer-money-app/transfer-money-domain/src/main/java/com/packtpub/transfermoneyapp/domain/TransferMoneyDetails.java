package com.packtpub.transfermoneyapp.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransferMoneyDetails implements Serializable {

    private String customerId;
    private String originAccountNumber;
    private String destinationAccountNumber;
    private int amount;
    private boolean externalBank;

}
