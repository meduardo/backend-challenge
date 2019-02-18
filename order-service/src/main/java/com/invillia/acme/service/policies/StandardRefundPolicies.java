package com.invillia.acme.service.policies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import com.invillia.acme.model.entity.Order;
import com.invillia.acme.model.entity.OrderStatus;

/**
 * Políticas de reembolbo padrão.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public enum StandardRefundPolicies implements RefundPolicy {
	
	BY_LIMIT_DATE () {
		@Override
		public boolean canRefund(Order orderToRefund) {
			
			Optional<LocalDateTime> foundDate = orderToRefund.getConfirmationDate();
			
			return foundDate.isPresent() 
				   && Duration.between(LocalDateTime.now(), foundDate.get())
				   			  .toDays() >= 15;
		}
	},
	
	BY_STATUS () {
		@Override
		public boolean canRefund(Order orderToRefund) {
			return OrderStatus.CONFIRMED.equals(orderToRefund.getStatus());
		}
	}
}
