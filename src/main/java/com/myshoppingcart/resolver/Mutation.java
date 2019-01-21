package com.myshoppingcart.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.myshoppingcart.model.Product;
import com.myshoppingcart.repository.ProductRepository;

//The mutation class performs changes on elements in the database (ex. purchasing an item)
public class Mutation implements GraphQLMutationResolver {
	
    private ProductRepository productRepository;

    public Mutation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //When we buy a product, this product must be non-empty
    //We return a null variable to indicate that the action is invalid
    public Product purchaseProduct(long id, int numberPurchase) {
    	
    	Product product = productRepository.findOne(id);
        if(product == null) {
        	//If the item wasn't found, we return a null variable.
        	return null;
        }
        
        int inventory = product.getInventory_count();
        
        if (inventory - numberPurchase < 0) {
        	//If we try to buy more than the inventory we return an null variable.
        	return null;
        }
        
        //Otherwise the action is valid and we return the updated value
        product.setInventory_count(inventory - numberPurchase);
        productRepository.save(product);

        return product;
    }

}
