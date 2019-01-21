package com.myshoppingcart;

import com.myshoppingcart.exception.GraphQLErrorAdapter;
import com.myshoppingcart.model.Product;
import com.myshoppingcart.repository.ProductRepository;
import com.myshoppingcart.resolver.Mutation;
import com.myshoppingcart.resolver.Query;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//This class is the main application. It runs a demo version to show the functionality of the application.

@SpringBootApplication
public class Application {

	public static Query generalQueries;
	public static Mutation generalMutations;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	//Error handler for the main application. This snippet of code was taken on graphql.org to handle
	//errors in the graphql instance. I did not write this code
	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}

	
	//Each of the following beans need to be managed by the Spring contrainer.
	
	@Bean
	public Query query(ProductRepository productRepository) {
		generalQueries = new Query(productRepository);
		return generalQueries;
	}
	
	@Bean
	public Mutation mutation(ProductRepository productRepository) {
		generalMutations = new Mutation(productRepository);
		return generalMutations;
	}
	
	@Bean
	public CommandLineRunner demo(ProductRepository productRepository) {
		return (args) -> {
			
			//To show the functionality of the application, we feed the GraphiQL with dummy values.
			//In a real application the database would be filled with existing values.
			//The unide ID is a counter that is incremented
			//You can add new products by entering a new name, price and inventory_count
			
			AtomicLong counter = new AtomicLong();
			
			Product product = new Product(counter.incrementAndGet(), "Apple", 20, 15);
			productRepository.save(product);
			
			product = new Product(counter.incrementAndGet(), "Banana", 23, 30);
			productRepository.save(product);
			
			product = new Product(counter.incrementAndGet(), "Bicycle", 5, 1);
			productRepository.save(product);
			
			product = new Product(counter.incrementAndGet(), "Coconut", 1, 0);
			productRepository.save(product);
			
		};
	}
}
