package com.myshoppingcart;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Iterables;
import com.myshoppingcart.model.Product;


@RestController
public class ProductController {
   
    //We perform a GET operation on a product with an ID
    @RequestMapping(value = "/fetchProduct", method = RequestMethod.GET)
    public ResponseEntity<Product> fetchProduct(@RequestParam(value="id", defaultValue="0") String id) {
    	
    	long longID = 0;
    	
    	//If the ID is invalid we return a BAD_REQUEST status
    	try {
    		longID = Long.parseLong(id);
    	}
    	catch (NumberFormatException E) {
    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    	}
    	
    	//If the ID is the default value we also return a BAD REQUEST status
    	if (longID == 0) 
    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    	
    	//Get the product based on the ID, if the ID is wrong, return BAD REQUEST status
    	Product product = Application.generalQueries.fetchProduct(longID);
    	if (product == null) 
    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    	
    	return new ResponseEntity<Product>(product,HttpStatus.OK);
    }
    
    //We perform a GET operation to get all products with a parameter for the empty products
    //By default we show empty products.
    @RequestMapping(value = "/fetchAllProducts", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> fetchAllProducts(@RequestParam(value="empty", defaultValue="1") String empty) {
    	
        //For the sake the demo we encode a parameter 0 as "don't show empty products" 
    	//and a parameter 1 (or anything else) as "show empty products"
        boolean emptyProducts = empty.equals("0") ? false : true;
        
        //Get all products
        Iterable<Product> productsList = Application.generalQueries.fetchAllProducts(emptyProducts);
        
        //If the list of all products is empty, return a BAD_REQUEST status
        if (productsList == null) {
        	return new ResponseEntity<Iterable<Product>>(HttpStatus.BAD_REQUEST);
        }
        else if (Iterables.size(productsList) == 0) 
    		return new ResponseEntity<Iterable<Product>>(HttpStatus.BAD_REQUEST);
    	
    	return new ResponseEntity<Iterable<Product>>(productsList,HttpStatus.OK);
    }
    
    //For the purchaseProduct method, a POST request would have been the appropriate solution.
    //However due to time constraint, I will have to use a GET method and pass a list of parameters
    //If time permits, I will change this implementation.
    @RequestMapping(value = "/purchaseProduct", method = RequestMethod.GET)
    public ResponseEntity<Product> purchaseProduct(@RequestParam("params") List<String> params) {
    	
    	long id = 0;
    	int numberPurchase = 0;
    	
    	//If the ID is invalid we return a BAD_REQUEST status
    	//same for the number of products to purchase
    	try {
    		
    		//We want only two parameters i.e. the ID and numberPurchase otherwise we return BAD_REQUEST
    		if (params.size() != 2) {
    			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    		}
    		
    		id = Long.parseLong(params.get(0));
    		numberPurchase = Integer.parseInt(params.get(1));
    		
    	}
    	catch (NumberFormatException E) {
    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    	}
    	
    	//If the ID is the default value or if we try to purchase 0 items, we return a BAD REQUEST status
    	if (id == 0 || numberPurchase == 0) 
    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    	
    	Product product = Application.generalMutations.purchaseProduct(id, numberPurchase);
    	
    	//Laslty, if the product is not found, return BAD_REQUEST
    	if (product == null) 
    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    	
    	return new ResponseEntity<Product>(product,HttpStatus.OK);
    }
    
}
