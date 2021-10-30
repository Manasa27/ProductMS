package com.team21.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.team21.utility.CompositeKey;

@Entity
@Table(name = "subscribed_product")
public class SubscribedProductEntity {

	@EmbeddedId
	private CompositeKey compositeId;
	private Integer quantity;

	// Getters and Setters
	public CompositeKey getCompositeId() {
		return compositeId;
	}

	public void setCompositeId(CompositeKey compositeId) {
		this.compositeId = compositeId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
