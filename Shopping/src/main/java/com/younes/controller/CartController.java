package com.younes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.younes.entity.Item;
import com.younes.entity.Product;
import com.younes.service.ProductService;

@Controller
@RequestMapping(value = "cart")
public class CartController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public String index(HttpSession session) {
		
		session.getAttribute("total");
		
		return "cart";
	}

	@GetMapping("/buy/{id}/{qty}")
	public String buyProduct(@PathVariable("id") String id, @PathVariable("qty") int qty,
			HttpSession session,Model model) {
		 productService = new ProductService();
		double total=0;
		if (session.getAttribute("cart") == null) {
			List<Item> cart = new ArrayList<Item>();
			cart.add(new Item(productService.find(id), qty));
			for(Item item:cart) {
				 total+=item.getQuantity()*item.getProduct().getPrice();
				
			}
			System.out.println("cart1: "+ total);
			
			session.setAttribute("cart", cart);
			session.setAttribute("total", total);
		} else {
			List<Item> cart = (List<Item>) session.getAttribute("cart");
			
			int index = this.exists(id, cart);
			if (index == -1) {

				cart.add(new Item(productService.find(id), qty));
				
			} else {
				
				cart.get(index).setQuantity(qty);
				
			}
			
			for(Item item:cart) {
				 total+=item.getQuantity()*item.getProduct().getPrice();
			}
			System.out.println("cart2: "+total);
			
			session.setAttribute("cart", cart);
			session.setAttribute("total", total);
		}
			
		return "cart";
	}
	
	@GetMapping("/buy/{id}")
	public String buy(@PathVariable("id") String id, 
			HttpSession session,Model model) {
		 productService = new ProductService();
		double total=0;
		if (session.getAttribute("cart") == null) {
			List<Item> cart = new ArrayList<Item>();
			cart.add(new Item(productService.find(id), 1));
			for(Item item:cart) {
				 total+=item.getQuantity()*item.getProduct().getPrice();
				
			}
			System.out.println("cart1: "+ total);
			
			session.setAttribute("cart", cart);
			session.setAttribute("total", total);
		} else {
			List<Item> cart = (List<Item>) session.getAttribute("cart");
			
			int index = this.exists(id, cart);
			if (index == -1) {

				cart.add(new Item(productService.find(id), 1));
				
			} else {
				
				int quantity = cart.get(index).getQuantity() + 1;
				cart.get(index).setQuantity(quantity);
				
			}
			
			for(Item item:cart) {
				 total+=item.getQuantity()*item.getProduct().getPrice();
			}
			System.out.println("cart2: "+total);
			
			session.setAttribute("cart", cart);
			session.setAttribute("total", total);
		}
		
		return "cart";
	}

	@GetMapping("/remove/{id}")
	public String remove(@PathVariable("id") String id, HttpSession session) {
		
		List<Item> cart = (List<Item>) session.getAttribute("cart");
		
		int index = this.exists(id, cart);
		cart.remove(index);
		double total=0;
		for(Item item:cart) {
			 total+=item.getQuantity()*item.getProduct().getPrice();
		}
		System.out.println("cart3: "+total);
		
		session.setAttribute("total", total);
		session.setAttribute("cart", cart);
		return "cart";
	}

	private int exists(String id, List<Item> cart) {
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getProduct().getId().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
	}


}
