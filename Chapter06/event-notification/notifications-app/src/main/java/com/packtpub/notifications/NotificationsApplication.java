package com.packtpub.notifications;

import com.packtpub.notifications.transfer.EventNotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;

import java.util.Map;

@Slf4j
@EnableBinding(EventNotificationChannel.class)
@SpringBootApplication
public class NotificationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsApplication.class, args);
    }

    @Bean
    IntegrationFlow integrationFlow(EventNotificationChannel eventNotificationChannel) {
        return IntegrationFlows.from(eventNotificationChannel.subscriptionOnMoneyTransferredChannel()).
                handle(String.class, new GenericHandler<String>() {
                    @Override
                    public Object handle(String payload, Map<String, Object> headers) {
                        log.info("Message retrieved:");
                        log.info(payload);
                        // TODO:
                        // Use the client id to find the transaction and determine the
                        // preferred notification channels
                        return null;
                    }
                }).get();
    }
}


