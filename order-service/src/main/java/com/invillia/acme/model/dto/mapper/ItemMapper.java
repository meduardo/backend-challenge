package com.invillia.acme.model.dto.mapper;

import org.springframework.stereotype.Component;

import com.invillia.acme.model.dto.ItemDTO;
import com.invillia.acme.model.entity.Item;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Component
public class ItemMapper implements DTOMapper<ItemDTO, Item>{

	@Override
	public ItemDTO toDTO(Item entity) {
		return new ItemDTO(entity.getId(), 
						   entity.getDescription(), 
						   entity.getPrice(),
						   entity.getAmount());
	}
 	
}
