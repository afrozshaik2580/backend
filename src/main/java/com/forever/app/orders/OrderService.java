package com.forever.app.orders;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forever.app.cart.CartItem;
import com.forever.app.cart.CartService;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderRepo orderRepo;

	@Transactional
	public ResponseEntity<String> placeOrder(Long userId, Address address) {
		
		List<CartItem> cartItems=cartService.getCart(userId).getBody();
		if(cartItems.size()==0) {
			return new ResponseEntity<String>("no items are present in the cart",HttpStatus.BAD_REQUEST);
		}
		double totalPrice=0;
		for(CartItem item : cartItems) {
			totalPrice += item.getUnitPrice()* item.getQuantity();
		}
		Order order=new Order(userId, LocalDateTime.now(), "Order Placed", "COD", cartItems,
				totalPrice, address);
		
		order = orderRepo.save(order);
		cartService.clearcart(userId);
		
		return new ResponseEntity<String>("order placed with orderid - " + order.getOrderId(),HttpStatus.CREATED);
		
	}

	public ResponseEntity<Order> getOrder(Long orderId, Long userId) {
		
		Order order=orderRepo.findById(orderId).orElse(null);
		if(order == null || order.getUserId() != userId) {
			return new ResponseEntity<Order>(order,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}

	public ResponseEntity<Order> deleteOrder(Long orderId, Long userId) {
		Order order=orderRepo.findById(orderId).orElse(null);
		if(order == null || order.getUserId() != userId) {
			return new ResponseEntity<Order>(order,HttpStatus.NOT_FOUND);
		}
		orderRepo.delete(order);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}

	public ResponseEntity<List<Order>> getAllOrders(Long userId) {
		return new ResponseEntity<List<Order>>(orderRepo.findByUserId(userId), HttpStatus.OK);
	}

	public ResponseEntity<?> manageOrders() {
		return new ResponseEntity<List<Order>>(orderRepo.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> updateOrderStatus(Long orderId, String status) {
		Order order = orderRepo.findById(orderId).orElse(null);
		if(order == null) {
			return new ResponseEntity<String>("order not found",HttpStatus.NOT_FOUND); 
		}
		order.setStatus(status);
		orderRepo.save(order);
		return new ResponseEntity<String>("order status updated", HttpStatus.OK);
	}

}
