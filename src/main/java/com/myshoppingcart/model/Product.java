package com.myshoppingcart.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//Model of a Product. It contains a unique ID, title, price and inventory count.
@Entity
public class Product {
	
	@Id
    private final long id;
    private final String title;
    private int price;
    private int inventory_count;

    public Product() {
    	this.id = Integer.MIN_VALUE;
    	title = null;
    }
    
    public Product(long id, String title, int price, int inventory_count) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.inventory_count = inventory_count;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getInventory_count() {
		return inventory_count;
	}

	public void setInventory_count(int inventory_count) {
		this.inventory_count = inventory_count;
	}
}
