package com.invillia.acme.event;

import java.io.Serializable;

/**
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public class PaymentStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long orderId;
	
	private String status;
	
	PaymentStatus() {
	}
	
	public PaymentStatus(final long id, final long orderId, final String status) {
		this.id = id;
		this.orderId = orderId;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getOrderId() {
		return orderId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
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
		PaymentStatus other = (PaymentStatus) obj;
		if (id != other.id)
			return false;
		if (orderId != other.orderId)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PaymentStatus [id=" + id + ", orderId=" + orderId + ", status=" + status + "]";
	}

	
}
