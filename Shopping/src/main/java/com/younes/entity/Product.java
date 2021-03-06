package com.younes.entity;



public class Product {

	private String id;
	private String name;
	private String photo;
	private double price;

	public Product() {
		super();
	}
	public Product(String name, String photo, double price) {
		super();
		this.name = name;
		this.photo = photo;
		this.price = price;
	}
	public Product(String id, String name, String photo, double price) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", photo=" + photo + ", price=" + price + "]";
	}
	
	
}
