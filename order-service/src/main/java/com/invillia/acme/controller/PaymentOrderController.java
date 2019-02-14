package com.invillia.acme.controller;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.event.PaymentRequest;
import com.invillia.acme.model.dto.PaymentDTO;
import com.invillia.acme.model.dto.mapper.PaymentMapper;
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
@RequestMapping("/v1/payments")
public class PaymentOrderController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentOrderController.class);
	
	private static final Supplier<Violation> INVALID_ORDER = () -> Violation.of("payment.invalid.order", "order.id");
 	
	@Autowired
	private OrderService orderService;

	@Autowired
	private PaymentMapper paymentMapper;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final ResponseEntity<PaymentDTO> createPayment(@RequestBody final PaymentDTO paymentInfo) {
		
		LOGGER.info("Requesting payment: [{}]", paymentInfo);
		
		Validator.with(
					HttpStatus.BAD_REQUEST.value(), 
					Validation.rule(() -> orderService.exists(paymentInfo.getOrderId()))
							  .onFail(INVALID_ORDER)
				 )
			 	 .execute();

		try {
			PaymentRequest created = orderService.requestPay(paymentInfo.getOrderId(), paymentInfo.getValue(), paymentInfo.getSomeSpecificInfo());
			
			LOGGER.info("Payment requested.: [{}]", created);
			
			return ResponseEntity.ok(paymentMapper.toDTO(created));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}