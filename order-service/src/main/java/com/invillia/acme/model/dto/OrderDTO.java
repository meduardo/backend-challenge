package com.invillia.acme.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.invillia.acme.model.entity.OrderStatus;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public class OrderDTO {
	
	private Long id;
	private String address;
	private List<ItemDTO> itens;
	private OrderStatus status;
	private Optional<LocalDateTime> confirmationDate;
	
	OrderDTO(){}

	public OrderDTO(final Long id, final String address, final List<ItemDTO> itens, final OrderStatus status, final Optional<LocalDateTime> confirmationDate) {
		this.id = id;
		this.address = address;
		this.itens = itens;
		this.status = status;
		this.confirmationDate = confirmationDate;
	}

	public final String getAddress() {
		return address;
	}

	public final List<ItemDTO> getItens() {
		return itens;
	}

	public final OrderStatus getStatus() {
		return status;
	}

	public final Optional<LocalDateTime> getConfirmationDate() {
		return confirmationDate;
	}

	public final Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setItens(List<ItemDTO> itens) {
		this.itens = itens;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setConfirmationDate(Optional<LocalDateTime> confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	
	
	
}
