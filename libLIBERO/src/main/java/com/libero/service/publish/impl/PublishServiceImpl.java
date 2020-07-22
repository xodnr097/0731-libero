package com.libero.service.publish.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.libero.service.domain.Product;
import com.libero.service.domain.User;
import com.libero.service.publish.PublishDAO;
import com.libero.service.publish.PublishService;

@Service("publishServiceImpl")
public class PublishServiceImpl implements PublishService{
	
	///Field
	@Autowired
	@Qualifier("publishDAOImpl")
	private PublishDAO publishDAO;
	public void setPublishDAO(PublishDAO publishDAO) {
		this.publishDAO = publishDAO;
	}
	
	///Constructor
	public PublishServiceImpl() {
		System.out.println(this.getClass());
	}
	
	public Map<String,Object> getPrintOptionList() throws Exception{
		
		List<User> list= publishDAO.getPrintOptionList();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		
		return map;
	}
	
	public void addPrintOption(Product product) throws Exception{
		publishDAO.addPrintOption(product);
	}
	
	public int getPublishNo(String creator) throws Exception {
		return publishDAO.getPublishNo(creator);
	}
	
	public void updateManu(Product product) throws Exception{
		publishDAO.updateManu(product);
	}
	
	public void updatePublishInfo() throws Exception{
		
	}
	
	public void updateRetailPrice() throws Exception{
		
	}
	
	public void addProduct(Product product) throws Exception{
		publishDAO.addProduct(product);
	}
	
	public Product getProduct(int prodNo) throws Exception{
		return publishDAO.getProduct(prodNo);
	}
	
	public void updateProduct(Product product) throws Exception{
		publishDAO.updateProduct(product);
	}
	
	public void addOptionPrice() throws Exception{
		
	}
	
	public void getOptionPrice() throws Exception{
		
	}
	
	public void updateOptionPrice() throws Exception{
		
	}

}
