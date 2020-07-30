package com.libero.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.libero.service.cart.CartService;
import com.libero.service.domain.Product;
import com.libero.service.domain.Review;
import com.libero.service.domain.User;
import com.libero.service.product.ProductService;
import com.libero.service.wish.WishService;

@Controller
@RequestMapping("/product/*")
public class ProductController{
	
	//Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("wishServiceImpl")
	private WishService wishService;
	
	@Autowired
	@Qualifier("cartServiceImpl")
	private CartService cartService;

	
	//Constructor
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	//method 서점메인화면 출력
	@RequestMapping(value="getBookList", method = RequestMethod.GET)
	public ModelAndView getBookList() throws Exception {
		
			System.out.println("/product/getBookList : GET");
			
			//BusinessLogic
			System.out.println("1");
			Map<String, Object> map=productService.getBookList();
			System.out.println("2");
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("book", map.get("list"));
			modelAndView.setViewName("forward:/view/product/getBookList.jsp");
			
			return modelAndView;
	}
	 ///* 카테고리별 서점화면 출력
	 @RequestMapping(value="getBookListByCategory/{bookCategory}", method = RequestMethod.GET)
	 public ModelAndView getBookListByCategory(@PathVariable String bookCategory) throws Exception {
		 		
		 System.out.println("카테고리 : "+bookCategory);
		 		
		 String category = null;
			if(bookCategory.equals("edu")) {
		 		category = "교육";
		 	}else if(bookCategory.equals("novel")) {
		 		category = "소설";
		 	}else if(bookCategory.equals("non")) {
		 		category = "비문학";	
		 	}else if(bookCategory.equals("poetry")) {
		 		category = "시";
		 	}else if(bookCategory.equals("essay")) {
		 		category = "수필";
		 	}
		 	
		 	System.out.println("카테고리 :: "+category);
		 	//BusinessLogic
		 	System.out.println("/product/getBookListByCategory : GET, pathVariable : "+category);
		 	
		 	Map<String, Object> map=productService.getBookListByCategory(category);
		 	System.out.println("컨트롤러 가져온것 :: "+map);
		 	System.out.println("컨트롤러 가져온것 2:: "+map.get("list"));
		 	ModelAndView modelAndView = new ModelAndView();
		 	
		 	//List<Product> product = (List<Product>) map.get("list");
		 	//product[0].get()
		 	//System.out.println(product.get(index).getBookCateogry);
		 	modelAndView.addObject("book", map.get("list"));
		 	modelAndView.setViewName("forward:/view/product/getBookListByCategory.jsp");
		 	
		 	return modelAndView;
		 	
	 }
	//*/
	 
	 
	//method 서비스상품화면 출력
		@RequestMapping(value="getProductList/{prodType}", method = RequestMethod.GET)
		public ModelAndView getProductList(@PathVariable String prodType) throws Exception {
			
				System.out.println("/product/getProductList : GET");
				
				//BusinessLogic
				System.out.println("1");
				Map<String, Object> map=productService.getProductList(prodType);
				System.out.println("2");
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.addObject("product", map.get("list"));
				modelAndView.setViewName("forward:/view/product/getProductList.jsp");
				
				return modelAndView;
		}
		
				//method 서비스상품화면 출력
				@RequestMapping(value="getProduct/{prodNo}", method = RequestMethod.GET)
				public ModelAndView getProduct(HttpSession session, @PathVariable int prodNo) throws Exception {
					System.out.println("/product/getProduct : GET");	
					
					String userId ="";
						//prodNo에 해당하는 도서정보 model에
						Product product=productService.getProduct(prodNo);
						ModelAndView modelAndView = new ModelAndView();
						modelAndView.addObject("product", product);
						System.out.println("회원/비회연 여부 확인");
					if(session.getAttribute("user") != null) {
						System.out.println("널 확인");
						User user = (User)session.getAttribute("user");
						System.out.println("널 확인2");
						userId = user.getUserId();
						HashMap <String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("prodNo", prodNo);
						hashMap.put("userId", userId);
						if(wishService.checkWish(hashMap) == true) {
							modelAndView.addObject("wish", "../../resources/images/product/wish/notsmile.png");
						}else {
							modelAndView.addObject("wish", "../../resources/images/product/wish/smile.png");
						}
					}else{
						   modelAndView.addObject("wish", "../../resources/images/product/wish/notsmile.png");
					}
						
						//상품타입에 따른 출력페이지
						System.out.println("상품타입은?"+product.getProdType());
						if((product.getProdType()).equals("paper")  ||  (product.getProdType()).equals("ebook")) {
							modelAndView.setViewName("forward:/view/product/getBook.jsp");
						}else {
							modelAndView.setViewName("forward:/view/product/getProduct.jsp");
						}
						
						
						//리뷰 데이터
						List<Review> review = productService.getReview(prodNo);
						modelAndView.addObject("review", review);
						
						
						
						return modelAndView;
				}
				
				//method 위시리스트 출력
				@RequestMapping(value="getWishList", method = RequestMethod.GET)
				public ModelAndView getWishList(HttpSession session) throws Exception {
					
						System.out.println("/product/getWishList : GET");
						
						User user = (User)session.getAttribute("user");
						String userId = user.getUserId();
						
						HashMap <String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("userId", userId);
						
						//BusinessLogic
						List<Product> wishList =wishService.getWishList(userId);
						ModelAndView modelAndView = new ModelAndView();
						modelAndView.addObject("wishList", wishList);
						modelAndView.addObject("userId", userId);
						
						modelAndView.setViewName("forward:/view/product/getWishList.jsp");
						
						return modelAndView;
				}
				
				//장바구니 리스트
				@RequestMapping(value="getCartList", method = RequestMethod.GET)
				public ModelAndView getCartList(HttpSession session) throws Exception {
					
						System.out.println("/product/getCartList : GET");
						
						User user = (User)session.getAttribute("user");
						String userId = user.getUserId();
						
						//HashMap <String, Object> hashMap = new HashMap<String, Object>();
						//hashMap.put("userId", userId);
						
						//BusinessLogic
						List<Product> cartList =cartService.getCartList(userId);
						System.out.println(cartList);
						System.out.println(cartList.size());
						List<Integer> buyNoList = new ArrayList<Integer>();
						System.out.println(buyNoList);
						
						
						for(int i=0 ; i<cartList.size() ; i++) {
							Product product = cartList.get(i);
							int buyNo = product.getBuyNo();
							buyNoList.add(buyNo);
							System.out.println(buyNoList);	
						}
					
							
						ModelAndView modelAndView = new ModelAndView();
						modelAndView.addObject("cartList", cartList);
						modelAndView.addObject("userId", userId);
						modelAndView.addObject("buyNoList", buyNoList);
						
						modelAndView.setViewName("forward:/view/product/getCartList.jsp");
						
						return modelAndView;
				}
				
			/*	
				@RequestMapping(value="getReviewList/{prodNo}", method = RequestMethod.GET)
				public ModelAndView getReviewList(@PathVariable int prodNo) throws Exception {
					
				}
			*/	
				
				//"/libero/product/CartTest"
				
				//장바구니 리스트
				@RequestMapping(value="cartTest", method = {RequestMethod.POST, RequestMethod.GET})
				public void getCartTest(@RequestParam("actualPrice") int actualPrice, @RequestParam("buyNoList") List buyNoList ) throws Exception {
					
						System.out.println("/product/getCartTest : POST");
						System.out.println("실제가격 :" + actualPrice);
						System.out.println("카트리스트 :" + buyNoList );
						System.out.println("buyNo"+buyNoList.indexOf(1));
						System.out.println("buyNo"+buyNoList.get(1));
						
						

						
				
				}
	
}//end class
