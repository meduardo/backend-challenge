package com.invillia.acme.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.invillia.acme.model.entity.Item;

public interface ItemService {

	boolean exists(final Long id);
	
	Optional<Item> findBy(final Long id);
	
	Item create(final String description, final BigDecimal price, final int ammount);
	
}
