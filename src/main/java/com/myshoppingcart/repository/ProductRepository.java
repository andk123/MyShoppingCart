package com.myshoppingcart.repository;

import org.springframework.data.repository.CrudRepository;
import com.myshoppingcart.model.Product;

//Data access object for a product. This will represent the list of all products.
public interface ProductRepository extends CrudRepository<Product, Long>{

}
