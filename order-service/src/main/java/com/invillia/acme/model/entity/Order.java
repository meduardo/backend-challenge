package com.invillia.acme.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Indexed
	private String address;

	@Reference
	private List<Item> itens;
	
	@Indexed
	private OrderStatus status;

	private Optional<LocalDateTime> confirmationDate;
	
	Order(){}

	public Order(final String address, final List<Item> itens, final OrderStatus status, final Optional<LocalDateTime> confirmationDate) {
		this.address = address;
		this.itens = itens;
		this.status = status;
		this.confirmationDate = confirmationDate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setConfirmationDate(Optional<LocalDateTime> confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public final String getAddress() {
		return address;
	}

	public final List<Item> getItens() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((confirmationDate == null) ? 0 : confirmationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (confirmationDate == null) {
			if (other.confirmationDate != null)
				return false;
		} else if (!confirmationDate.equals(other.confirmationDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", address=" + address + ", itens=" + itens + ", status=" + status
				+ ", confirmationDate=" + confirmationDate + "]";
	}

	
	
}
