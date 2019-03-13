package com.packtpub.projectreactordemo.mono;

import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class MonoDemoTest {

    @Test
    public void givenAnEmptyMono_WhenTheDefaultIfEmptyOperatorIsUsed_ThenTheDefaultValueIsDeliveredAsResult() throws Exception {
        String defaultMessage = "Hello world";
        Mono<String> emptyMonoMessageProduced = Mono.empty();

        Mono<String> monoMessageDelivered = emptyMonoMessageProduced.defaultIfEmpty(defaultMessage);

        monoMessageDelivered.subscribe(messageDelivered ->
                Assert.assertEquals(defaultMessage, messageDelivered));
    }

}
