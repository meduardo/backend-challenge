package com.invillia.acme.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;

import com.invillia.acme.model.entity.Order;

/**
 * Encaminha o pedido para confirmação de pagamento.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@EnableBinding({Source.class})
public class OrderPaymentsProducer {
	
    private final Source source;

    public OrderPaymentsProducer(Source source) {
        this.source = source;
    }

    public void requestPayment(Order order) {
        source.output()
        	  .send(MessageBuilder.withPayload(order)
        			  			  .build()
        	  );
    }
}