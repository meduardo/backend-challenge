package com.invillia.acme.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.invillia.acme.event.PaymentRequest;
import com.invillia.acme.model.entity.Item;
import com.invillia.acme.model.entity.Order;

public interface OrderService {
	
	Order save(final Order order); 
	
	boolean exists(final long id);
	
	Optional<Order> findBy(final long id);
	
	Order create(final String address, final List<Item> itens);
	
	PaymentRequest requestPay(final long id, final BigDecimal value, final String someInformation);
}
