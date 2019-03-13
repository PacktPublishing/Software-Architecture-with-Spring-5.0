package com.packtpub.springdatamongoreactive.persistence;

import com.packtpub.springdatamongoreactive.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
}
