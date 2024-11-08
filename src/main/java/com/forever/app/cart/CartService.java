package com.forever.app.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forever.app.products.Product;
import com.forever.app.products.ProductService;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductService productService;
	
	public ResponseEntity<Long> createCart(Long userId) {
		Cart cart=cartRepository.save(new Cart(userId));
		return new ResponseEntity<Long>(cart.getCartId(),HttpStatus.CREATED);
	}

	public ResponseEntity<String> addToCart(Long userId, Long productId, String size) {
		Cart cart=cartRepository.findByUserId(userId);
		
		if(cart==null) {
			return new ResponseEntity<String>("cart not found", HttpStatus.NOT_FOUND);
		}
		
		List<CartItem> items=cart.getItems();
		System.out.println(items);
		for(CartItem currItem:items) {
			if(currItem.getProduct().getId() == productId && currItem.getSize().equals(size)) {
				currItem.setQuantity(currItem.getQuantity() + 1);
				cartRepository.save(cart);
				return new ResponseEntity<String>("added to cart", HttpStatus.OK);
			}
		}
		
		Product product;
		try {
			product=productService.getProductDetails(productId).getBody();
			
		} catch (Exception e) {
			return new ResponseEntity<String>("product not found", HttpStatus.NOT_FOUND);
		}
		CartItem item = new CartItem();
		item.setUnitPrice(product.getPrice());
		item.setQuantity(1);
		item.setProduct(product);
		item.setSize(size);
		items.add(item);
		cart.setItems(items);
		cartRepository.save(cart);
		
		return new ResponseEntity<String>("added to cart", HttpStatus.OK);
	}
	
	public ResponseEntity<String> updateCart(Long userId, Long productId, String size, Integer quantity) {
		Cart cart=cartRepository.findByUserId(userId);
		
		if(cart==null) {
			return new ResponseEntity<String>("cart not found", HttpStatus.NOT_FOUND);
		}
		
		List<CartItem> items=cart.getItems();
		
		for(CartItem currItem:items) {
			if(currItem.getProduct().getId() == productId && currItem.getSize().equals(size)) {
				if(quantity==0) {
					items.remove(currItem);
					cartItemRepository.deleteById(currItem.getId());
				}
				else {
					currItem.setQuantity(quantity);
				}
				cartRepository.save(cart);
				return new ResponseEntity<String>("updated cart", HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<String>("cartitem not found", HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<List<CartItem>> getCart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		
		List<CartItem> items=new ArrayList<>();
		
		if(cart==null) {
			return new ResponseEntity<List<CartItem>>(items,HttpStatus.NOT_FOUND);
		}	
		
		items=cart.getItems();
		return new ResponseEntity<List<CartItem>>(items,HttpStatus.OK);
	}
	

	public void clearcart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		cart.setItems(null);
		cartRepository.save(cart);
	}

	
}
