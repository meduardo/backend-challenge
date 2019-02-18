package com.invillia.acme.messaging;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

import com.invillia.acme.event.PaymentStatus;
import com.invillia.acme.model.entity.OrderStatus;
import com.invillia.acme.service.OrderService;

/**
 * Processa os pagamentos confirmados
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@EnableBinding(Sink.class)
public class PaymentsListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsListener.class);
    
	@Autowired
	private OrderService orderService;
	
	@PostConstruct
	public void log() {
	}
	
    @StreamListener(target = Sink.INPUT)
    public void processPayment(Message<PaymentStatus> paymentStatus) {
        
    	PaymentStatus result = paymentStatus.getPayload();
    	
        LOGGER.info("Payment Status: {}", result);
        
        //TODO ter uma classe espcifica de status de pagamento 
        // ou um classe de estratégia do que fazer x status.
        
        if(!"OK".equalsIgnoreCase(result.getStatus())){
        	return;
        }
        
    	orderService.findBy(result.getOrderId())
    				.map(order -> {
    					
    					order.setStatus(OrderStatus.CONFIRMED);
    					order.setConfirmationDate(Optional.ofNullable(LocalDateTime.now()));
    					
    					LOGGER.info("Payment confirmed!");
    					return order;
    				})
    				.ifPresent(orderService::save);
        	
    	//TODO here we could send message do Store do dispatch de order, ou something like that.
    }
 
}