package com.invillia.acme.model.dto.mapper;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invillia.acme.model.dto.OrderDTO;
import com.invillia.acme.model.entity.Order;

/**
 * Responsável por mapear objetos de entidade em DTO, e vice-versa.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Component
public class OrderMapper implements DTOMapper<OrderDTO, Order>{

	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public final OrderDTO toDTO(final Order entity) {
		
		return new OrderDTO(entity.getId(), 
							entity.getAddress(),
							entity.getItens()
							      .stream()
							      .filter(Objects::nonNull)
							      .map(itemMapper::toDTO)
							      .collect(Collectors.toList()),
							entity.getStatus(),
							entity.getConfirmationDate()
			  );
	}
	
}
