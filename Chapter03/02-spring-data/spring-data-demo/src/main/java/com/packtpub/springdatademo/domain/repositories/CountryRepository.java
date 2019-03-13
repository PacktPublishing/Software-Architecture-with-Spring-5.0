package com.packtpub.springdatademo.domain.repositories;

import com.packtpub.springdatademo.domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findByName(String name);
}
