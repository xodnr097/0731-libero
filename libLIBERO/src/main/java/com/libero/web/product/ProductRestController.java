package com.libero.web.product;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.libero.service.cart.CartService;
import com.libero.service.domain.User;
import com.libero.service.product.ProductService;
import com.libero.service.wish.WishService;


@RestController
@RequestMapping("/product/*")
public class ProductRestController {

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
			public ProductRestController() {
				System.out.println(this.getClass());
			}
			
			@Value("#{commonProperties['path2']}")
			String path;
			
			//method 좋아요 등록
			@RequestMapping(value="json/addWish", method = RequestMethod.POST)
			public String addWish(int prodNo, String userId) throws Exception {
				
					System.out.println("/product/addWish : POST");
					System.out.println("전달된 prodNo : "+prodNo);
					System.out.println("전달된 아이디 : "+userId );
					JSONObject obj = new JSONObject();
					//BusinessLogic
					
					HashMap <String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("prodNo", prodNo);
					hashMap.put("userId", userId);
					
					
					//좋아요 존재 여부 검사
					boolean isWish = wishService.addWish(hashMap);
					
					if(isWish == true) {
						obj.put("wishImage", "../../resources/images/product/wish/smile.png");
					}else {
						obj.put("wishImage", "../../resources/images/product/wish/notsmile.png");
					}
										
					
					return obj.toJSONString();
			}//end addWish
			
			//장바구니 등록
			@RequestMapping(value="json/addCart", method = RequestMethod.POST)
			public String addCart(int prodNo, String userId, int buyAmount, String from, String prodType) throws Exception {
				
					System.out.println("/product/addWish : POST");
					System.out.println("전달된 prodNo : "+prodNo);
					System.out.println("전달된 아이디 : "+userId );
					System.out.println("전달된 구매수량"+buyAmount);
					System.out.println("어디서 왔는가"+from); //장바구니 페이지에서 온 경우 수량을 업데이트함
					JSONObject obj = new JSONObject();
					//BusinessLogic
					
					HashMap <String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("prodNo", prodNo);
					hashMap.put("userId", userId);
					hashMap.put("buyAmount", buyAmount);
					hashMap.put("from", from);
					hashMap.put("prodType", prodType);
					
					if(from.equals("cart")) {
						hashMap.put("from", from);
					}	
					cartService.addCart(hashMap);
			
					String message = "장바구니에 등록되었습니다.";
					
					obj.put("result", message);
					
					return obj.toJSONString();
			}//end addCart
			
			
			//장바구니 삭제
			@RequestMapping(value="json/removeCart", method = RequestMethod.POST)
			public String addCart(int prodNo, String userId) throws Exception {
				
					System.out.println("/product/addWish : POST");
					System.out.println("전달된 prodNo : "+prodNo);
					System.out.println("전달된 아이디 : "+userId );
					JSONObject obj = new JSONObject();
					//BusinessLogic
					
					HashMap <String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("prodNo", prodNo);
					hashMap.put("userId", userId);
					
					cartService.removeCart(hashMap);
			
					String message = "장바구니에서 삭제되었습니다.";
					
					obj.put("result", message);
					
					return obj.toJSONString();
			}//end addCart
			
					//리뷰 등록
					@RequestMapping(value="json/addReview", method = RequestMethod.POST)
					public String addReview(HttpSession session, String reviewContent, String userId, int starRate, int buyNo) throws Exception {
							
						System.out.println("/product/addReview : POST");
						//User user = (User)session.getAttribute("user");
						//String userId = user.getUserId();
						System.out.println("아이디 : "+userId);
						System.out.println("별점 : "+starRate);
						System.out.println("내용 : "+reviewContent);
						System.out.println("buyNo"+buyNo);
						
							
							HashMap <String, Object> hashMap = new HashMap<String, Object>();
							
							hashMap.put("userId", userId);
							hashMap.put("buyNo", buyNo);
							hashMap.put("starRate", starRate);
							hashMap.put("reviewContent", reviewContent);
							productService.addReview(hashMap);
							
							
							//return obj.toJSONString();
							JSONObject obj = new JSONObject();
							String message = "리뷰가 등록";
							obj.put("message", message);
							
							return obj.toJSONString();
			}//end addReview
					
					
					//리뷰 이미지 등록
					@RequestMapping(value="json/addReviewImage", method = RequestMethod.POST)
					public String addReviewImage(@RequestParam("files") MultipartFile multipartFile, @RequestParam("buyNo") int buyNo) throws Exception {
							
					
						System.out.println("/product/addReviewImage : POST");
						System.out.println("리뷰 이미지"+multipartFile);
						System.out.println(buyNo);
						System.out.println(multipartFile.getOriginalFilename());
						
						
						
						if(!multipartFile.isEmpty()) {
									//String fileName = fileUpload(request);
									//System.out.println(fileName);
							HashMap <String, Object> hashMap = new HashMap<String, Object>();
							
							MultipartFile uploadedFile = multipartFile;
									
									String originalFileName = uploadedFile.getOriginalFilename(); //오리지날 파일명
									String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자
									String fileRoot = path+"product/fileUpload/review/"; //파일 경로
									String savedFileName = UUID.randomUUID() + extension; //저장될 파일 명
									
									hashMap.put("reviewImage", savedFileName);
									hashMap.put("buyNo", buyNo);
											
									File f = new File(fileRoot+savedFileName);
									
								    uploadedFile.transferTo(f);
								    
								    productService.addReviewImage(hashMap);
						}//end if
						

				
						
							//ProductService.addReview(hashMap);
							
							//return obj.toJSONString();
							JSONObject obj = new JSONObject();
							String message = "리뷰가 등록";
							obj.put("message", message);
							
							return obj.toJSONString();
			}//end addReview
			
			public String fileUpload(MultipartHttpServletRequest request) throws Exception{
				
				Map<String, MultipartFile> files = request.getFileMap();
				CommonsMultipartFile cmf = (CommonsMultipartFile) files.get("file");
				
				String originalFileName = cmf.getOriginalFilename(); //오리지날 파일명
				String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자
				String fileRoot = path+"product/fileUpload/review/"; //파일 경로
				String savedFileName = UUID.randomUUID() + extension; //저장될 파일 명
				
			File f = new File(fileRoot+savedFileName);
			cmf.transferTo(f);
			
					return savedFileName;
			}
			
			
}//end class