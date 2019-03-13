package com.packtpub.projectreactordemo.flux;

import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FluxDemoTest {

    @Test
    public void givenAListOfCapitalizedStrings_WhenTheFlatMapConvertsToUpperCaseTheStrings_ThenTheStringsAreInUpperCase() throws Exception {
        List<String> namesCapitalized =
                Arrays.asList("John", "Steve", "Rene");
        Iterator<String> namesCapitalizedIterator =
                namesCapitalized.iterator();
        Flux<String> fluxWithNamesCapitalized =
                Flux.fromIterable(namesCapitalized);

        Flux<String> fluxWithNamesInUpperCase = fluxWithNamesCapitalized
                .map(name -> name.toUpperCase());

        fluxWithNamesInUpperCase.subscribe(nameInUpperCase -> {
                    String expectedString =
                            namesCapitalizedIterator.next().toUpperCase();

                    Assert.assertEquals(expectedString, nameInUpperCase);
                }
        );
    }
}
