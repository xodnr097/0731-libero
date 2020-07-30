<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
				<title>상품리뷰</title>
				<jsp:include page="/common/cdn.jsp"></jsp:include>
				<style>
				     		.starR1{
							    background: url('../../resources/images/product/star_review.png') no-repeat -52px 0;
							    background-size: auto 100%;
							    width: 15px;
							    height: 30px;
							    float:left;
							    text-indent: -9999px;
							    cursor: pointer;
							}
							.starR2{
							    background: url('../../resources/images/product/star_review.png') no-repeat right 0;
							    background-size: auto 100%;
							    width: 15px;
							    height: 30px;
							    float:left;
							    text-indent: -9999px;
							    cursor: pointer;
							}
							.starR1.on{background-position:0 0;}
							.starR2.on{background-position:-15px 0;}
							
								.file-field.medium .file-path-wrapper {
								height: 3rem; }
								.file-field.medium .file-path-wrapper .file-path {
								height: 2.8rem; }
								
								.file-field.big-2 .file-path-wrapper {
								height: 3.7rem; }
								.file-field.big-2 .file-path-wrapper .file-path {
								height: 3.5rem; }
																	
				</style>
</head>
<body>


<ul class="navbar-nav">
				<li class="nav-item" id="review">
					<a class="nav-link" href="#" data-toggle="modal" data-target="#addReviewModal">리뷰 등록</a>
				</li>
</ul>
<!-- 리뷰등록 모달창 -->				
<div class="modal fade" id="addReviewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
		<div class="modal-content">
				<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">리뷰 등록</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
						</button>
				
				</div>
				<div class="modal-body">
						<form name="addReviewForm">
								<div class="form-group">
								<label for="recipient-name" class="col-form-label">별점</label>
									<div class="starRev">
									  <span class="starR1 on" id="5" >별1_왼쪽</span>
									  <span class="starR2" id="10" >별1_오른쪽</span>
									  <span class="starR1" id="15" >별2_왼쪽</span>
									  <span class="starR2" id="20" >별2_오른쪽</span>
									  <span class="starR1" id="25" >별3_왼쪽</span>
									  <span class="starR2" id="30" >별3_오른쪽</span>
									  <span class="starR1" id="35" >별4_왼쪽</span>
									  <span class="starR2" id="40" >별4_오른쪽</span>
									  <span class="starR1" id="45" >별5_왼쪽</span>
									  <span class="starR2" id="50" >별5_오른쪽</span>
									  <input type="hidden" id="starRate" name="starRate" value="">
									</div></br></br>
								
								<!--Material textarea-->
									
									<div class="md-form">
										  <textarea id="textarea-char-counter" class="form-control md-textarea" length="120" rows="3" name="reviewContent">하이요</textarea>
										  <label for="textarea-char-counter" id="review">Type your text</label>
										  <input type="hidden" id="reviewContent" name="reviewContent">
									</div>
								</div>
								
								<div class="md-form" action="#">
								  <div class="file-field">
								    <div class="btn blue-gradient btn-sm float-left">
								      <span><i class="fas fa-cloud-upload-alt mr-2" aria-hidden="true"></i>Choose files</span>
								      <input type="file" name='uploadFile' multiple>
								    </div>
								    <div class="file-path-wrapper">
								      <input class="file-path validate" type="text" placeholder="Upload one or more files">
								    </div>
								  </div>
								</div>
						</form>
				</div>
				
				
				
				<div class="modal-footer">
						<button type="button" class="btn btn-cyan" id="addButton">등록</button>
						<button type="button" class="btn btn-outline-info waves-effect" data-dismiss="modal">취소</button>
				</div>
			</div>
			</div>
		</div>		
	</body>
	<script type="text/javascript">
	
				//별점 설정 이벤트
				$('.starRev span').click(function(){
					  $(this).parent().children('span').removeClass('on');
					  $(this).addClass('on').prevAll('span').addClass('on');
					  //var content = $("#textarea-char-counter").text();   실험
					  var starRate = $(this).attr("id");
					  
					  $("#starRate").val(starRate);
					  //var star = $("#starRate").val(); 실험
					  //alert(star); 실험
					  //alert(starRate); 실험
					  
					  return false;
				});//end starRev click function
				
				//등록 버튼 클릭 이벤트
				$('#addButton').click(function(){
					
					var starRate = $('span[class*=on]:last').attr("id"); //별점이 찍힌 가장 마지막 span의 아이디값
					alert(starRate);
					var content = $("#textarea-char-counter").val(); // 입력한 리뷰내용
					alert(content);
					
					
					
						
						//리뷰 내용, 별점
						$.ajax({
							url : "/libero/product/json/addReview",
							type: "POST",
							dataType: "json",
							header : {
										"Accept" : "application/json",
										"Content-Type" : "application/json"
									 },
							data: {"userId": "${sessionScope.user.userId}", "starRate" : starRate, "reviewContent" : content, "buyNo" : 10001},
							success : function(data, success){
								
								
								var message = data.message;
								alert(message);
								$('#addReviewModal').modal("hide");
								window.location.reload();
								alert("성공");
								
							}//end success
						});//end ajax
						
					var formData = new FormData();
					var inputFile = $("input[name='uploadFile']");
					var files = inputFile[0].files;
						formData.append('files', files[0]);
						formData.append('buyNo', 10001);
						
						alert(files);
						alert(formData.get('buyNo'));
						
						
						$.ajax({
							url : "/libero/product/json/addReviewImage",
							processData : false,
							contentType : false,
							data : formData,
							type: "POST",
							success : function(data, success){
								
								
								var message = data.message;
								alert(message);
								window.location.reload();
								alert("성공2");
								
							}//end success
						});//end ajax	
					
				})//end addbutton click
				
	</script>
</html>