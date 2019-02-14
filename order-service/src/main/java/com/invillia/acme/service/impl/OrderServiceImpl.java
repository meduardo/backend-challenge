package com.invillia.acme.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.invillia.acme.event.PaymentRequest;
import com.invillia.acme.messaging.PaymentsChannels;
import com.invillia.acme.model.entity.Item;
import com.invillia.acme.model.entity.Order;
import com.invillia.acme.model.entity.OrderStatus;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private PaymentsChannels paymentsEventBus;

	@Autowired
	private OrderRepository orderRepo;
	
	@Override
	public Optional<Order> findBy(long id) {
		return orderRepo.findById(id);
	}

	@Override
	public Order create(String address, List<Item> itens) {
		return orderRepo.save(
					  new Order(address, itens, OrderStatus.CREATED, Optional.empty())		
			   );
	}

	@Override
	public boolean exists(long id) {
		return orderRepo.existsById(id);
	}

	@Override
	public PaymentRequest requestPay(long id, final BigDecimal value, final String someSpecificInfo) {
		
		return orderRepo.findById(id)
						.filter(order -> OrderStatus.CREATED.equals(order.getStatus()))
						.map(order -> {
							//Poderiamos usar um controle de transação, acks, para garantir a 
							//enrega da informação, antes de marcar como aguardando pagamento.
							order.setStatus(OrderStatus.WAITTING_PAYMENT);
							return order;
						})
						.map(orderRepo::save)
						.map(order -> new PaymentRequest(id, value, someSpecificInfo))
						.map(this::sendPayment)
						.orElseThrow(() -> new RuntimeException()); //TODO usar exceção de negócio
	}
	
	public PaymentRequest sendPayment(final PaymentRequest payment) {
       
        paymentsEventBus.outboundPayments()
        				.send(MessageBuilder.withPayload(payment)
							                .setHeader(MessageHeaders.CONTENT_TYPE, 
							                		   MimeTypeUtils.APPLICATION_JSON)
							                .build()
					    );
        LOGGER.info("Sended: {}", payment);
        return payment;
    }


}
