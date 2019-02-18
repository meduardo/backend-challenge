package com.invillia.acme.service.policies;

import java.util.Arrays;
import java.util.List;

import com.invillia.acme.model.entity.Order;

/**
 * Permite encadear a execução de regras de reembolso
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public final class ChainedRefundPolicy implements RefundPolicy {
	
	private final List<RefundPolicy> policies;
	
	private ChainedRefundPolicy(final List<RefundPolicy> policies) {
		this.policies = policies;
	}

	public static final ChainedRefundPolicy with(final RefundPolicy ... policies ) {
		// Programação defensiva - fail fast
		
		if (policies == null || policies.length == 0) {
			throw new IllegalArgumentException("Refund Policies cannot be null or empty, verify!");
		}
	
		return new ChainedRefundPolicy(Arrays.asList(policies));
	}
	
	@Override
	public final boolean canRefund(final Order orderToRefund) {
		
		for (final RefundPolicy policy : policies) {
			if (!policy.canRefund(orderToRefund)) {
				return false;
			}
		}
		return true;
	}
	
}