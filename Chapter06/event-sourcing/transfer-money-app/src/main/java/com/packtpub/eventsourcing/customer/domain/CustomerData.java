package com.packtpub.eventsourcing.customer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CustomerData {

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("initial_amount")
    private int initialAmount;

    private String name;
}
