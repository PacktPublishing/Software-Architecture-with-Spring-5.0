package com.packtpub.transfermoneyapp.transfer.domain;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransferMoneyDetails implements Serializable {

    private String customerId;
    private String originAccountNumber;
    private String destinationAccountNumber;
    private int amount;
    private boolean externalBank;

}
