package com.packtpub.demohateoas.countries.boundaries;

import com.packtpub.demohateoas.countries.domain.BankStatement;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class BankStatementController {

    private Map<Integer, List<BankStatement>> clientsBankStatements;

    public BankStatementController() {
        clientsBankStatements = new HashMap<>();
        clientsBankStatements.put(1, buildBankStatements(1, 2, 3));
        clientsBankStatements.put(2, buildBankStatements(10, 11, 12, 13));
        clientsBankStatements.put(3, buildBankStatements(20, 21));
    }

    @RequestMapping(value = "/customer/{id}/bankStatements", method = RequestMethod.GET)
    public HttpEntity<Resources<BankStatement>> findBankStatements(
            @PathVariable(value = "id") int id) {
        List<BankStatement> bankStatements = clientsBankStatements.get(id);
        for (BankStatement bankStatement : bankStatements) {
            bankStatement.add(linkTo((methodOn(BankStatementController.class).markAsFailed(id, bankStatement.getBankStatementId()))).withRel("markAsFailed"));
            bankStatement.add(linkTo((methodOn(BankStatementController.class).resend(id, bankStatement.getBankStatementId()))).withRel("resend"));
        }
        return new ResponseEntity<>(new Resources<>(bankStatements), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{customerId}/bankStatements/{bankStatementId}/markAsFailed", method = RequestMethod.PUT)
    public HttpEntity<Void> markAsFailed(
            @PathVariable(value = "customerId") int customerId,
            @PathVariable(value = "bankStatementId") int bankStatementId) {
        System.out.println("Marking as failed");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{customerId}/bankStatements/{bankStatementId}/resend", method = RequestMethod.POST)
    public HttpEntity<Void> resend(
            @PathVariable(value = "customerId") int customerId,
            @PathVariable(value = "bankStatementId") int bankStatementId) {
        System.out.println("Resending");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<BankStatement> buildBankStatements(Integer... ids) {
        List<BankStatement> bankStatements = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            bankStatements.add(new BankStatement(ids[i], "Some information here"));
        }
        return bankStatements;
    }


}
