package com.team21.utility;

import java.io.Serializable;
import java.util.Objects;

public class CompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String buyerId;
	protected String prodId;

	// Constructors
	public CompositeKey() {
		super();
	}

	public CompositeKey(String buyerId, String prodId) {
		super();
		this.buyerId = buyerId;
		this.prodId = prodId;
	}

	// Getters and Setters
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// Utility Methods

	@Override
	public int hashCode() {
		return Objects.hash(buyerId, prodId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeKey other = (CompositeKey) obj;
		return Objects.equals(buyerId, other.buyerId) && Objects.equals(prodId, other.prodId);
	}

}
