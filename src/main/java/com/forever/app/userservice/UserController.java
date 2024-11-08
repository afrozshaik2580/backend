package com.forever.app.userservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.app.cart.CartItem;
import com.forever.app.dto.TokenDetails;
import com.forever.app.orders.Address;
import com.forever.app.orders.Order;

@RestController
@RequestMapping("user")
public class UserController {
	
	private final ObjectMapper objectMapper=new ObjectMapper(); 

	@Autowired
	private UserService userService;

	 
	@GetMapping("cart")
	public ResponseEntity<List<CartItem>> getCartfromUserId(@RequestAttribute("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException {
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.getcartItemsFromUserId(tokenDetails.getUserId());
	}
	
	@PutMapping("cart/add")
	public ResponseEntity<String> addToCart(@RequestBody Map<String, Object> request, @RequestAttribute("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException {
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		Long productid =  Long.valueOf(request.get("id").toString());
		String size = request.get("size").toString();
		return userService.addCartItemtoUserId(tokenDetails.getUserId(), productid, size);
	}
	
	@PutMapping("cart/update")
	public ResponseEntity<String> updateCart(@RequestBody Map<String, Object> request, @RequestAttribute("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException {
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		Long productId =  Long.valueOf(request.get("productId").toString());
		String size = request.get("size").toString();
		Integer quantity = Integer.valueOf(request.get("quantity").toString());
		return userService.updateCartItemtoUserId(tokenDetails.getUserId(), productId, size, quantity);
	}
	
	@PostMapping("placeorder")
	public ResponseEntity<String> placeOrder(@RequestAttribute("X-User-Details") String userDetailsString, @RequestBody Address address) throws JsonMappingException, JsonProcessingException{
		System.out.println(address);
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.placeOrder(tokenDetails.getUserId(), address);
	}
	
	@GetMapping("orders/all")
	public ResponseEntity<List<Order>> getAllOrders(@RequestAttribute("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.getAllOrders(tokenDetails.getUserId());
	}
	
	@GetMapping("orders/get/{orderId}")
	public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId, @RequestAttribute("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.getOrderDetails(orderId, tokenDetails.getUserId());
	}
	
	@DeleteMapping("orders/delete/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId, @RequestAttribute("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.deleteOrder(orderId, tokenDetails.getUserId());
	}
}
