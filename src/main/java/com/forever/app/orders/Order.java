package com.forever.app.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forever.app.cart.CartItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
//@Data
//@NoArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	private Long userId;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime orderDate;
	private String status;
	private String paymentMethod;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private List<CartItem> orderItems;

	private double totalPrice;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	
	
	public Order(Long userId, LocalDateTime orderDate, String status, String paymentMethod, List<CartItem> orderItems,
			double totalPrice, Address address) {
		super();
		this.userId = userId;
		this.orderDate = orderDate;
		this.status = status;
		this.paymentMethod = paymentMethod; 
		this.orderItems = new ArrayList<>(orderItems);
		this.totalPrice = totalPrice;
		this.address = address;
	}
	
	public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = new ArrayList<>(orderItems); // Clone the collection
    }

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(Long orderId, Long userId, LocalDateTime orderDate, String status, String paymentMethod,
			List<CartItem> orderItems, double totalPrice, Address address) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.orderDate = orderDate;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.orderItems = orderItems;
		this.totalPrice = totalPrice;
		this.address = address;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<CartItem> getOrderItems() {
		return orderItems;
	}


}
