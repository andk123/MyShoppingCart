package com.myshoppingcart.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void noParamFetchProductShouldReturnError() throws Exception {

        this.mockMvc.perform(get("/fetchProduct")).andDo(print()).andExpect(status().isBadRequest());
    }
    
    @Test
    public void defaultParamFetchProductShouldReturnError() throws Exception {
        this.mockMvc.perform(get("/fetchProduct").param("id", "0")).andDo(print()).andExpect(status().isBadRequest());
    }
    
    @Test
    public void fetchProductWithParamShouldBeOk() throws Exception {

    	this.mockMvc.perform(get("/fetchProduct").param("id", "1")).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void fetchProductWithInvalidParamShouldReturnError() throws Exception {

    	this.mockMvc.perform(get("/fetchProduct").param("id", "HelloWorld")).andExpect(status().isBadRequest());
    }
    
    @Test
    public void noParamFetchAllProductsShouldBeOk() throws Exception {

        this.mockMvc.perform(get("/fetchAllProducts")).andExpect(status().isOk());
        
    }
    
    @Test
    public void fetchAllProductsWithParamShouldBeOk() throws Exception {

    	this.mockMvc.perform(get("/fetchAllProducts").param("id", "1")).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void fetchAllProductsWithInvalidParamShouldAlsoBeOk() throws Exception {

    	this.mockMvc.perform(get("/fetchAllProducts").param("id", "ShouldWork")).andExpect(status().isOk());
    }

    @Test
    public void noParamPurchaseProductShouldReturnError() throws Exception {

        this.mockMvc.perform(get("/purchaseProduct")).andExpect(status().isBadRequest());
    }
    
    @Test
    public void purchaseProductWithParamShouldBeOk() throws Exception {

    	this.mockMvc.perform(get("/purchaseProduct").param("params", "1", "1")).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void purchaseProductWithNotEnoughParamShouldReturnError() throws Exception {

    	this.mockMvc.perform(get("/purchaseProduct").param("params", "1")).andExpect(status().isBadRequest());
    }
    
    @Test
    public void purchaseProductWithTooManyParamShouldReturnError() throws Exception {

    	this.mockMvc.perform(get("/purchaseProduct").param("params", "1", "1", "2")).andExpect(status().isBadRequest());
    }

}
