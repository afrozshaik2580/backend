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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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


}
