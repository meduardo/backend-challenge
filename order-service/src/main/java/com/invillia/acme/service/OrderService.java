package com.invillia.acme.service;

import java.util.List;
import java.util.Optional;

import com.invillia.acme.model.entity.Item;
import com.invillia.acme.model.entity.Order;

public interface OrderService {
	
	Optional<Order> findBy(final long id);
	
	Order create(final String address, final List<Item> itens);
}
