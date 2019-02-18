package com.invillia.acme.controller;

import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.model.entity.Order;
import com.invillia.acme.service.OrderService;
import com.invillia.acme.service.RefundService;
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
@RequestMapping("/v1/refunds")
public class RefundController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefundController.class);
	
	private static final Supplier<Violation> NOT_FOUND_ORDER = () -> Violation.of("order.id.notfound", "order.id");
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RefundService refundService;
	
	@PostMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final ResponseEntity<Void> refundOrder(@PathVariable final long orderId) {
		
		LOGGER.info("Creating refund for order, content: [{}]", orderId);
		
		Optional<Order> found = orderService.findBy(orderId);
		
		Validator.with(HttpStatus.NOT_FOUND.value(),
				   Validation.rule(found::isPresent)
				   			 .onFail(NOT_FOUND_ORDER))
			 .execute();
		
		Order order = found.get();
		Validator.with(HttpStatus.PRECONDITION_FAILED.value(),
				   Validation.rule(() -> refundService.canRefund(order))
				   			 .onFail(NOT_FOUND_ORDER))
			 .execute();
		
		try {
			refundService.refund(order);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}