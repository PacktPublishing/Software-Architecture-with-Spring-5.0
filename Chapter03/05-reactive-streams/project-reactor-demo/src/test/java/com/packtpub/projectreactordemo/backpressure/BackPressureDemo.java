package com.packtpub.projectreactordemo.backpressure;

import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class BackPressureDemo {

    @Test
    public void givenAFluxWith10Elements_WhenBackPressureAsksFor3Elements_ThenTheChunksHaveTheSize_3_3_3_1() throws Exception {
        List<Integer> digitsArray = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
        Flux<Integer> fluxWithDigits = Flux.fromIterable(digitsArray);

        fluxWithDigits.buffer(3)
                .log()
                .subscribe(elements -> {
                            Assert.assertTrue(elements.size() <= 3);
                        }
                );
    }
}
