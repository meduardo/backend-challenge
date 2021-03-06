package com.invillia.acme.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.event.PaymentRequest;
import com.invillia.acme.messaging.PaymentsProducer;
import com.invillia.acme.model.entity.Item;
import com.invillia.acme.model.entity.Order;
import com.invillia.acme.model.entity.OrderStatus;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.service.OrderService;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private PaymentsProducer paymentsProducer;

	@Autowired
	private OrderRepository orderRepo;
	
	@Override
	public final Optional<Order> findBy(final long id) {
		return orderRepo.findById(id);
	}

	@Override
	public Order create(final String address, final List<Item> itens) {
		return orderRepo.save(
					  new Order(address, itens, OrderStatus.CREATED, Optional.empty())		
			   );
	}

	@Override
	public final Order save(final Order order) {
		return orderRepo.save(order);
	}
	
	@Override
	public final boolean exists(final long id) {
		return orderRepo.existsById(id);
	}

	@Override
	public final PaymentRequest requestPay(long id, final BigDecimal value, final String someSpecificInfo) {
		
		LOGGER.info("Requesting payment: Order: [{}], Value:[{}], Other Info: [{}]", id, value, someSpecificInfo);
		
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
						.map(paymentsProducer::sendPayment)
						.orElseThrow(() -> new RuntimeException()); //TODO usar exceção de negócio
	}
	
}
