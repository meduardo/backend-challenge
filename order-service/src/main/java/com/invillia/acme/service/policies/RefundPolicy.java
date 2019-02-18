package com.invillia.acme.service.policies;

import com.invillia.acme.model.entity.Order;

/**
 * Políticas de reembolso de pedidos.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public interface RefundPolicy {
	
	boolean canRefund(final Order orderToRefund);
	
}
