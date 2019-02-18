package com.invillia.acme.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.entity.Order;
import com.invillia.acme.service.RefundService;
import com.invillia.acme.service.policies.ChainedRefundPolicy;
import com.invillia.acme.service.policies.RefundPolicy;
import com.invillia.acme.service.policies.StandardRefundPolicies;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Service
public class RefundServiceImpl implements RefundService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RefundServiceImpl.class);

	private static final RefundPolicy REFUND_POLICY = ChainedRefundPolicy.with(
													  		StandardRefundPolicies.BY_STATUS, 
															StandardRefundPolicies.BY_LIMIT_DATE
													  );
	
	@Override
	public boolean canRefund(Order order) {
		
		LOGGER.info("Checking refund, order: {}", order);
		
		return REFUND_POLICY.canRefund(order);
	}

	@Override
	public void refund(Order order) {
		LOGGER.info("Refund order... {}", order);
		// Here we could send a message (some spcific classe, like RefundRequest) to another service like payment service to make transfer, 
	}
	
}
