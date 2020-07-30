package com.libero.service.product;

import java.util.HashMap;
import java.util.List;

import com.libero.common.Search;
import com.libero.service.domain.Product;
import com.libero.service.domain.Review;

public interface ProductDAO {

	//public Product getProduct(int prodNo) throws Exception;
	
	public List<Product> getBookList() throws Exception;

	public List<Product> getBookListByCategory(String category);
	
	public List<Product> getProductList(String prodType) throws Exception;

	public Product getProduct(int prodNo);

	public void addReview(HashMap<String, Object> hashMap);

	public void addReviewImage(HashMap<String, Object> hashMap);

	public List<Review> getReview(int prodNo);
	
	
}
