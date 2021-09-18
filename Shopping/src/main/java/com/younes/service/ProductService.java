package com.younes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.younes.entity.Product;
@Service
public class ProductService {

	List<Product> products;
	
	public List<Product> findAll() {
		return getAllProduct();
	}
	public List<Product> getAllProduct(){
		products=new ArrayList<>();
		products.add(new Product("p01", "Apple", "apple.jpg", 100));
		products.add(new Product("p02", "Condor", "condor.jpg", 60));
		products.add(new Product("p03", "Laptop", "laptop.jpg", 200));
		products.add(new Product("p04", "Condor Pro", "condorpro.jpg", 150));
		return products;
	}

	public Product find(String id) {
		List<Product> products=getAllProduct();
		for (Product product :products) {
			if (product.getId().equalsIgnoreCase(id)) {
				return product;
			}
		}
		return null;
	}
	
	
}
