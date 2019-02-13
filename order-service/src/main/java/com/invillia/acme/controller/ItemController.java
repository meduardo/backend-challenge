package com.invillia.acme.controller;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.model.dto.ItemDTO;
import com.invillia.acme.model.dto.mapper.ItemMapper;
import com.invillia.acme.model.entity.Item;
import com.invillia.acme.service.ItemService;
import com.invillia.acme.validation.Validation;
import com.invillia.acme.validation.Validator;
import com.invillia.acme.validation.Violation;

/**
 * 
 * Controller responsável por obter as informações dos itens desejados.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@RestController
@RequestMapping("/v1/itens")
public class ItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	
	private static final Supplier<Violation> INVALID_DESCRIPTION = () -> Violation.of("item.description.invalid.value", "order.address");
	private static final Supplier<Violation> INVALID_PRICE = () -> Violation.of("item.price.invalid.value", "item.price");
	private static final Supplier<Violation> INVALID_AMOUNT = () -> Violation.of("item.amount.invalid.value", "item.amount");
	private static final Supplier<Violation> INVALID_ITEM = () -> Violation.of("item.invalid.value", "item");
 	
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemMapper itemMapper;
	
	@GetMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<ItemDTO> findItem(@PathVariable final long itemId) {
		
		LOGGER.info("Searching item - id: [{}]", itemId);
		
		return itemService.findBy(itemId)
						  .map(itemMapper::toDTO)
						  .map(ResponseEntity::ok)
						  .orElseGet(ResponseEntity.notFound()::build);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final ResponseEntity<ItemDTO> createItem(@RequestBody final ItemDTO itemDTO) {
		
		LOGGER.info("Creating item, content: [{}]", itemDTO);
		
		Validator.with(
				HttpStatus.BAD_REQUEST.value(), 
				Validation.rule(() -> Objects.nonNull(itemDTO))
				 		  .onFail(INVALID_ITEM)
			 )
		 	 .execute();

		
		Validator.with(
					HttpStatus.BAD_REQUEST.value(), 
				   	checkDescription(itemDTO.getDescription()),
				    checkPrice(itemDTO.getPrice()),
				    checkAmount(itemDTO.getAmount())
				 )
			 	 .execute();

		try {
			Item created = itemService.create(itemDTO.getDescription(), 
											  itemDTO.getPrice(),
											  itemDTO.getAmount());
			
			return ResponseEntity.ok(itemMapper.toDTO(created));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private Validation checkDescription(final String desc) {
		return Validation.rule(() -> desc != null && !desc.trim().isEmpty())
						 .onFail(INVALID_DESCRIPTION);
	}
	
	private Validation checkPrice(final BigDecimal price) {
		return Validation.rule(() -> Objects.nonNull(price) && price.compareTo(BigDecimal.ZERO) > 0)
						 .onFail(INVALID_PRICE);
	}
	
	private Validation checkAmount(final int amount) {
		return Validation.rule(() -> Objects.nonNull(amount) && amount >= 0)
						 .onFail(INVALID_AMOUNT);
	}
}