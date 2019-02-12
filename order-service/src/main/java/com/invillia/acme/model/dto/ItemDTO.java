package com.invillia.acme.model.dto;

import java.math.BigDecimal;

public class ItemDTO {

	private Long id;
	private String description;
	private BigDecimal price;
	private int amount;

	ItemDTO() {}

	public ItemDTO(Long id, String description, BigDecimal price, int amount) {
		this.id = id;
		this.description = description;
		this.price = price;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}

