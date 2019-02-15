package com.invillia.acme.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

import com.invillia.acme.event.PaymentRequest;

/**
 * Processa os pagamentos
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@EnableBinding(Processor.class)
public class PaymentListener {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentListener.class);
    
    @SendTo(Processor.OUTPUT)
    @StreamListener(target = Processor.INPUT)
    public Message<String> processPayment(Message<PaymentRequest> paymentRequest) {
        
        LOGGER.info("Processing payment: {}", paymentRequest);

        return MessageBuilder.withPayload("-SOME-RESULT-MESSAGE-")
                			 .build();
    }

}
