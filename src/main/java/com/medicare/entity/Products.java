package com.medicare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Products {
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 64)
	@NotEmpty(message = "required")
	private String image;
	
	@Column(length = 128, nullable = false, unique = true)
	@NotEmpty(message = "required")
	private String name;
	
	@Column(length = 128, nullable = false)
	@NotEmpty(message = "required")
	private String brand;
	
	@NotNull(message = "required")
	@Column(nullable = false)
	private int availability;
	
	@Column(nullable = false)
	@NotNull(message = "required")
	private int price;
	private boolean enabled;	

	public Products() {

	}

	public Products(String image, String name, String brand, int availability, int price, boolean enabled) {
		this.image = image;
		this.name = name;
		this.brand = brand;
		this.availability = availability;
		this.price = price;
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public String getPhotosImagePath() {		
		return "/product-photos/" + this.id + "/" + this.image;
	}	

}
