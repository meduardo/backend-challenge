package com.invillia.acme.event;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public class PaymentRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long orderId;
	
	private BigDecimal value;
	
	private String someSpecificInfo;
	
	PaymentRequest() {
	}
	
	public PaymentRequest(final long orderId, final BigDecimal value, final String someSpecificInfo) {
		this.orderId = orderId;
		this.value = value;
		this.someSpecificInfo = someSpecificInfo;
	}
	
	public long getOrderId() {
		return orderId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getSomeSpecificInfo() {
		return someSpecificInfo;
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

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setSomeSpecificInfo(String someSpecificInfo) {
		this.someSpecificInfo = someSpecificInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((someSpecificInfo == null) ? 0 : someSpecificInfo.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		PaymentRequest other = (PaymentRequest) obj;
		if (id != other.id)
			return false;
		if (orderId != other.orderId)
			return false;
		if (someSpecificInfo == null) {
			if (other.someSpecificInfo != null)
				return false;
		} else if (!someSpecificInfo.equals(other.someSpecificInfo))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PaymentRequest [id=" + id + ", orderId=" + orderId + ", value=" + value + ", someSpecificInfo="
				+ someSpecificInfo + "]";
	}
	
	
	
}
