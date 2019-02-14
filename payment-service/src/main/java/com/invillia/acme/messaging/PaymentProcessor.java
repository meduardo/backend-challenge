package com.invillia.acme.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.invillia.acme.event.PaymentRequest;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Component
public class PaymentProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessor.class);
	
    @StreamListener("payments-processed")
    public void handlePayments(@Payload PaymentRequest paymentRequest) {
        LOGGER.info("Received payment request: {}", paymentRequest);
    }
}
