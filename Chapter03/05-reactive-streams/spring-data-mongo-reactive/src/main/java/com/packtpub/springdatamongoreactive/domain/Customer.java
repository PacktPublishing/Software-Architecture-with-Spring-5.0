package com.packtpub.springdatamongoreactive.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document
public class Customer {

    @Id
    private String id;

    private String name;

    public Customer(String name) {
        this.name = name;
    }
}
