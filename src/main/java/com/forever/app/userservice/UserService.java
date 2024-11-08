package com.forever.app.userservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forever.app.cart.CartItem;
import com.forever.app.cart.CartService;
import com.forever.app.orders.Address;
import com.forever.app.orders.Order;
import com.forever.app.orders.OrderService;


@Service
public class UserService {
	
	@Autowired
	private CartService cartServiceClient;
	
	@Autowired
	private OrderService orderServiceClient;

	public ResponseEntity<String> addCartItemtoUserId(Long userId, Long productId, String size) {
		ResponseEntity<String> response;
		try {
			response = cartServiceClient.addToCart(userId, productId, size);
		} catch (Exception e) {
			return new ResponseEntity<String>("product not found", HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	public ResponseEntity<List<CartItem>> getcartItemsFromUserId(Long userId) {
		List<CartItem> cartItems=cartServiceClient.getCart(userId).getBody();	
		return new ResponseEntity<List<CartItem>>(cartItems,HttpStatus.OK);
	}

	public ResponseEntity<String> placeOrder(Long userId, Address address) {
		return orderServiceClient.placeOrder(userId, address);
	}

	public ResponseEntity<Order> getOrderDetails(Long orderId, Long userId) {
		return orderServiceClient.getOrder(orderId, userId);
	}

	public ResponseEntity<Order> deleteOrder(Long orderId, Long userId) {
		return orderServiceClient.deleteOrder(orderId,userId);
	}

	public ResponseEntity<List<Order>> getAllOrders(Long userId) {
		return orderServiceClient.getAllOrders(userId);
	}

	public ResponseEntity<String> updateCartItemtoUserId(Long userId, Long productId, String size, Integer quantity) {
		return cartServiceClient.updateCart(userId, productId, size, quantity);
	}

}
