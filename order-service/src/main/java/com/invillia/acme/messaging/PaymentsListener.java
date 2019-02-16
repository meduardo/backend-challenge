package com.invillia.acme.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

import com.invillia.acme.event.PaymentStatus;

/**
 * Processa os pagamentos confirmados
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@EnableBinding(Sink.class)
public class PaymentsListener {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsListener.class);
    
    @StreamListener(target = Sink.INPUT)
    public void processPayment(Message<PaymentStatus> paymentStatus) {
        
        LOGGER.info("Payment Status: {}", paymentStatus.getPayload());
    }
 
}