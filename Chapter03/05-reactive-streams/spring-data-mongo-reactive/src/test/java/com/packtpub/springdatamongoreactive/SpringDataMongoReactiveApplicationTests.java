package com.packtpub.springdatamongoreactive;

import com.packtpub.springdatamongoreactive.domain.Customer;
import com.packtpub.springdatamongoreactive.persistence.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataMongoReactiveApplicationTests {

    @Autowired
    CustomerRepository repository;

    @Autowired
    ReactiveMongoOperations operations;

    @Before
    public void setUp() {
        operations.collectionExists(Customer.class)
                .flatMap(exists -> exists ? operations.dropCollection(Customer.class) : Mono.just(exists))
                .flatMap(o -> operations.createCollection(Customer.class))
                .then()
                .block();

    }

    @Test
    public void findAllShouldFindTheTotalAmountOfRecordsInserted() {
        int quantityOfEntitiesToPersistAsFlux = 100;

        // Saving a Flux with 100 items
        repository
                .saveAll(
                        Flux.just(
                                generateArrayWithElements(quantityOfEntitiesToPersistAsFlux)))
                .then()
                .block();

        // Saving a Mono
        repository.saveAll(Mono.just(new Customer("Rene")))
                .then()
                .block();

        int totalAmountOfInserts = quantityOfEntitiesToPersistAsFlux + 1;

        repository
                .findAll()
                .map(customer -> customer.getId())
                .collectList()
                .subscribe(data ->
                        Assert.assertEquals(totalAmountOfInserts, data.size())
                );

    }

    private Customer[] generateArrayWithElements(int quantity) {
        Customer[] customers = new Customer[quantity];
        for (int i = 0; i < quantity; i++) {
            customers[i] = new Customer(i + "_customer");
        }
        return customers;
    }

}
