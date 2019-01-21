package com.myshoppingcart.resolver;

import java.util.LinkedList;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.myshoppingcart.model.Product;
import com.myshoppingcart.repository.ProductRepository;

//This class performs queries on the database without updating or deleting any elements.
public class Query implements GraphQLQueryResolver {
	private ProductRepository productRepository;

	public Query(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	//This method fetch all the products in the database
	public Iterable<Product> fetchAllProducts(boolean emptyProducts) {
		
		Iterable<Product> listProducts = productRepository.findAll();
		
		//If we want empty and non-empty products we return the full list
		if (emptyProducts) {
			return listProducts;
		}
		else {
			
			//Otherwise we iterate through the list to get the non-empty elements.
			//However, this is not an efficient way. To use only on a small data set.
			List<Product> nonEmptyProducts = new LinkedList<Product>();
			for (Product product : listProducts) {
				if (product.getInventory_count() > 0) {
					nonEmptyProducts.add(product);
				}
			}
			return nonEmptyProducts;
		}
	}
	
	//Return a single product based on its ID
	public Product fetchProduct(long id) {
        return productRepository.findOne(id);
    }
	
}
