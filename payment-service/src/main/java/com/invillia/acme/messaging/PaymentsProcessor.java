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
import com.invillia.acme.event.PaymentStatus;

/**
 * Processa os pagamentos
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@EnableBinding(Processor.class)
public class PaymentsProcessor {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsProcessor.class);
    
    @SendTo(Processor.OUTPUT)
    @StreamListener(target = Processor.INPUT)
    public Message<PaymentStatus> processPayment(Message<PaymentRequest> paymentRequest) {
        
    	PaymentRequest request = paymentRequest.getPayload();
    	
        LOGGER.info("Processing payment: {}", request);
        
        return MessageBuilder.withPayload(new PaymentStatus(request.getId(), request.getOrderId(), "sucess"))
                			 .build();
    }

}
