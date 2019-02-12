package com.invillia.acme.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.entity.Item;
import com.invillia.acme.model.entity.Order;
import com.invillia.acme.model.entity.OrderStatus;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

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

}
