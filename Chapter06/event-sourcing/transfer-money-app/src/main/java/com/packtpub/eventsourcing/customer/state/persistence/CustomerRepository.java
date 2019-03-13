package com.packtpub.eventsourcing.customer.state.persistence;

import com.packtpub.eventsourcing.customer.state.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
