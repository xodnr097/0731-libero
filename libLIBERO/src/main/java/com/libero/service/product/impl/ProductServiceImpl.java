package com.libero.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.libero.service.domain.Product;
import com.libero.service.domain.Review;
import com.libero.service.product.ProductDAO;
import com.libero.service.product.ProductService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{

	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAO;
	public void setProductDAO(ProductDAO productDAO) {
					this.productDAO = productDAO;
	}
	
	///Constructor
	public ProductServiceImpl() {
		// TODO Auto-generated constructor stub
					System.out.println(this.getClass());
	}

	///Method
	@Override
	public Map<String, Object> getBookList() throws Exception {
		// TODO Auto-generated method stub
		List<Product> list = productDAO.getBookList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		System.out.println(list);
		
		return map;
	}

	@Override
	public Map<String, Object> getBookListByCategory(String Category) throws Exception {
		List<Product> list = productDAO.getBookListByCategory(Category);
		System.out.println(list);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		
		return map;
	}
	
	@Override
	public Map<String, Object> getProductList(String prodType) throws Exception {
		List<Product> list = productDAO.getProductList(prodType);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		
		return map;
	}



	@Override
	public Product getProduct(int prodNo) {
		// TODO Auto-generated method stub
		
		return productDAO.getProduct(prodNo);
	}



	@Override
	public void addReview(HashMap<String, Object> hashMap) {
		productDAO.addReview(hashMap);
		
	}
	
	public void addReviewImage(HashMap<String, Object> hashMap) {
		productDAO.addReviewImage(hashMap);
	}

	@Override
	public List<Review> getReview(int prodNo) {
		
		return productDAO.getReview(prodNo);
		
	}
	

}
