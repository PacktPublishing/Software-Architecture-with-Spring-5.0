package com.packtpub.demohateoas.countries.boundaries;

import com.packtpub.demohateoas.countries.domain.Customer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CustomerController {

    private Map<Integer, String> customers;

    public CustomerController() {
        customers = new HashMap<>();
        customers.put(1, "Rene Enriquez");
        customers.put(2, "John Smith");
        customers.put(3, "Kent Larson");
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public HttpEntity<Customer> findById(
            @PathVariable(value = "id") int id) {
        Customer customer = new Customer(id, customers.get(id));
        customer.add(linkTo((methodOn(CustomerController.class).findById(id))).withSelfRel());
        customer.add(linkTo((methodOn(BankStatementController.class).findBankStatements(id))).withRel("bankStatements"));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


}
