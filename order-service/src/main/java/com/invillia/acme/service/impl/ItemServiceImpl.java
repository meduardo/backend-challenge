package com.invillia.acme.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.entity.Item;
import com.invillia.acme.repository.ItemRepository;
import com.invillia.acme.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public final Optional<Item> findBy(final Long id) {
		return itemRepository.findById(id);
	}

	@Override
	public final Item create(final String description, final BigDecimal price, final int amount) {
		return itemRepository.save(new Item(description, price, amount));
	}

	@Override
	public boolean exists(Long id) {
		return itemRepository.existsById(id);
	}

}
