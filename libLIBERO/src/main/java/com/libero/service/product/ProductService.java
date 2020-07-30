package com.libero.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libero.service.domain.Product;
import com.libero.service.domain.Review;

public interface ProductService {	
	//public Product getProduct(int prodNo) throws Exception;
	
	//public Map<String, Object> getProductList(Search search) throws Exception;

	public Map<String, Object> getBookList() throws Exception;

	public Map<String, Object> getBookListByCategory(String bookCategory) throws Exception;

	public Map<String, Object> getProductList(String prodType) throws Exception;

	//public Product getBook(int prodNo);

	public Product getProduct(int prodNo);

	public void addReview(HashMap<String, Object> hashMap) ;

	public void addReviewImage(HashMap<String, Object> hashMap);

	public List<Review> getReview(int prodNo);

	
}
