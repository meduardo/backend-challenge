package com.invillia.acme.model.dto.mapper;

import org.springframework.stereotype.Component;

import com.invillia.acme.model.dto.ItemDTO;
import com.invillia.acme.model.entity.Item;

@Component
public class ItemMapper implements DTOMapper<ItemDTO, Item>{

	@Override
	public ItemDTO toDTO(Item entity) {
		// TODO Auto-generated method stub
		return null;
	}
 	
}
