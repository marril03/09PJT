package com.model2.mvc.service.product;


import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductService {
	// INSERT
	public void addProduct(Product product) throws Exception ;

		// SELECT ONE
	public Product getProduct(int prodNo) throws Exception;

		// SELECT LIST
	public Map<String , Object> getProductList(Search search) throws Exception ;

		// UPDATE
	public void updateProduct(Product product) throws Exception ;
	
}
