package com.invillia.acme.model.dto.mapper;

import org.springframework.stereotype.Component;

import com.invillia.acme.event.PaymentRequest;
import com.invillia.acme.model.dto.PaymentDTO;

/**
 * Responsável por mapear objetos de entidade em DTO, e vice-versa.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Component
public class PaymentMapper implements DTOMapper<PaymentDTO, PaymentRequest>{

	@Override
	public PaymentDTO toDTO(final PaymentRequest entity) {
		return new PaymentDTO(entity.getId(),
							  entity.getOrderId(),
							  entity.getValue(),
							  entity.getSomeSpecificInfo()
			   );
	}
	
}
