package com.forever.app.products;


import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
//@Data
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Name of the product cannot be empty")
	private String name;
	
	@NotBlank(message = "Description should not be blank")
	@Size(min = 5,  message = "Description should be more than 5 characters")
	private String description;
	
	@DecimalMin(value = "1.0", message = "Price should be minimum $1.0")
	private double price;
	
	@NotBlank(message = "Category should not be blank")
	private String category;
	
	private String subCategory;
	
	@Column(name = "best_seller")
	private boolean bestSeller;
	
	@ElementCollection
	@CollectionTable(name = "product_sizes")
	private List<String> sizes;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image", columnDefinition = "TEXT")
    private List<String> images;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(long id, @NotBlank(message = "Name of the product cannot be empty") String name,
			@NotBlank(message = "Description should not be blank") @Size(min = 5, message = "Description should be more than 5 characters") String description,
			@DecimalMin(value = "1.0", message = "Price should be minimum $1.0") double price,
			@NotBlank(message = "Category should not be blank") String category, String subCategory, boolean bestSeller,
			List<String> sizes, List<String> images) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.subCategory = subCategory;
		this.bestSeller = bestSeller;
		this.sizes = sizes;
		this.images = images;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public boolean isBestSeller() {
		return bestSeller;
	}

	public void setBestSeller(boolean bestSeller) {
		this.bestSeller = bestSeller;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
