package com.invillia.acme.messaging;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;  

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public interface PaymentsChannels {
	
	@Input("payments-processed")
	SubscribableChannel inboundPayments();
	
	@Output("payments-to-process")
	MessageChannel outboundPayments();
}