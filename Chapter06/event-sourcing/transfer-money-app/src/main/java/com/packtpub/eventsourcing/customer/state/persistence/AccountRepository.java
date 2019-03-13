package com.packtpub.eventsourcing.customer.state.persistence;

import com.packtpub.eventsourcing.customer.state.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

}
