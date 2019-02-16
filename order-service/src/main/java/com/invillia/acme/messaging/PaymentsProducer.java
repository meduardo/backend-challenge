package com.invillia.acme.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.invillia.acme.event.PaymentRequest;
import com.invillia.acme.service.impl.OrderServiceImpl;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Component
@EnableBinding(Source.class)
public class PaymentsProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsProducer.class);
	
	@Autowired
	private Source source;

	public PaymentRequest sendPayment(final PaymentRequest payment) {
	       
        source.output()
			  .send(MessageBuilder.withPayload(payment)
				                  .setHeader(MessageHeaders.CONTENT_TYPE, 
				                  		     MimeTypeUtils.APPLICATION_JSON)
				                  .build()
		      );
        
        LOGGER.info("Sended: {}", payment);
        return payment;
    }
    
}