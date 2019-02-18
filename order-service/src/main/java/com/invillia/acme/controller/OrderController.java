package com.invillia.acme.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
import com.invillia.acme.model.dto.OrderDTO;
import com.invillia.acme.model.dto.mapper.OrderMapper;
import com.invillia.acme.model.entity.Item;
import com.invillia.acme.model.entity.Order;
import com.invillia.acme.service.ItemService;
import com.invillia.acme.service.OrderService;
import com.invillia.acme.validation.Validation;
import com.invillia.acme.validation.Validator;
import com.invillia.acme.validation.Violation;

/**
 * 
 * Controller responsável por obter as informações dos pedidos desejados.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@RestController
@RequestMapping("/v1/orders")
public class OrderController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	private static final Supplier<Violation> INVALID_ADDRESS = () -> Violation.of("order.address.invalid.value", "order.address");
	private static final Supplier<Violation> INVALID_ITENS = () -> Violation.of("order.itens.invalid.value", "order.itens");
	private static final Supplier<Violation> NOT_FOUND_ITEM = () -> Violation.of("order.itens.notfound", "order.itens");
	private static final Supplier<Violation> NOT_FOUND_ORDER = () -> Violation.of("order.id.notfound", "order.id");
 	
	@Autowired
	private OrderService orderService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderMapper orderMapper;
	
	@GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<OrderDTO> findOrder(@PathVariable final long orderId) {
		
		LOGGER.info("Searching order - id: [{}]", orderId);
		
		Optional<OrderDTO> found = orderService.findBy(orderId)
				   							   .map(orderMapper::toDTO);
		
		Validator.with(HttpStatus.NOT_FOUND.value(),
					   Validation.rule(found::isPresent)
					   			 .onFail(NOT_FOUND_ORDER))
				 .execute();
		
		return found.map(ResponseEntity::ok)
					.orElseGet(ResponseEntity.notFound()::build);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final ResponseEntity<OrderDTO> createOrder(@RequestBody final OrderDTO orderDTO) {
		
		LOGGER.info("Creating order, content: [{}]", orderDTO);
		
		Validator.with(
					HttpStatus.BAD_REQUEST.value(), 
				   	checkAddress(orderDTO.getAddress()),
				    hasItens(orderDTO.getItens())
				 )
			 	 .execute();

		List<Optional<Item>> itens = orderDTO.getItens()
									  		 .stream()
											 .map(ItemDTO::getId)
											 .map(itemService::findBy)
										     .collect(Collectors.toList());
		
		Validator.with(HttpStatus.BAD_REQUEST.value(), checkItensExist(itens))
		         .execute();
		
		try {
			Order created = orderService.create(
											orderDTO.getAddress(),
											itens.stream()
												 .map(Optional::get)
												 .collect(Collectors.toList())
										 );
			LOGGER.info("Created order : [{}]", created);
			return ResponseEntity.ok(orderMapper.toDTO(created));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	private Validation checkItensExist(final List<Optional<Item>> itens) {
		return Validation.rule(() -> itens.stream()
										  .allMatch(Optional::isPresent))
						 .onFail(NOT_FOUND_ITEM);
	}
	
	private Validation checkAddress(final String address) {
		return Validation.rule(() -> address != null && !address.trim().isEmpty())
						 .onFail(INVALID_ADDRESS);
	}
	
	private Validation hasItens(final List<ItemDTO> itens) {
		return Validation.rule(() -> Objects.nonNull(itens) && !itens.isEmpty())
						 .onFail(INVALID_ITENS);
	}

}