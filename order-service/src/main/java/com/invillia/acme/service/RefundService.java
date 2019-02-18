package com.invillia.acme.service;

import com.invillia.acme.model.entity.Order;

public interface RefundService {
	
	boolean canRefund(final Order order);

	void refund(final Order order);

}
