<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
       body > div.container{
        	border: 3px solid #D6CDB7;
            margin-top: 10px;
        }
    </style>
    
<script type="text/javascript">

//카카오 주소 API 사용
    function daumjuso() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
//                     if(extraAddr !== ''){
//                         extraAddr = ' (' + extraAddr + ')';
//                     }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById("postCode").value = data.zonecode;
                document.getElementById("address").value = data.zonecode+addr+extraAddr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("phone1").focus();
            }
        }).open();
    }


//////// 아이디 중복 체크 //////////////////////////////////////////////   
//     $(function(){
// 		$("input[name=userId]").on("keyup", function(){
// 			var userId = $("input[name=userId]").val();
// 			if(userId == ""){
// 				$("img[name=CheckDuplicationImg]").attr("src", "");
// 				$("input[name=CheckDuplication]").val("false");
// 			}else{
// 				$.ajax(
// 						{
// 							url : "/user/json/duplicationUser/"+userId,
// 							method : "GET",
// 							dataType : "json",
// 							headers : {
// 								"Accept" : "application/json",
// 								"Content-Type" : "application/json"
// 							},
// 						success : function(JSONData , status){
// 							var check = JSONData;
							
// 							if(check){
// 								$("img[name=CheckDuplicationImg]").attr("src", "/images/uploadFiles/duplication_o.png");
// 								$("input[name=CheckDuplication]").val("true");
// 							}else{
// 								$("img[name=CheckDuplicationImg]").attr("src", "/images/uploadFiles/duplication_x.png");
// 								$("input[name=CheckDuplication]").val("false");
// 							}
// 						}
// 					});
// 			}
// 		});
// 	});
	//예비 코드
	
	
	$(function (){
		$("#emailcheck").on("click",function(){
			$.ajax(
					{	url : "/libero/user/json/emailSend?userId="+$("#userId").val(),
						method : "GET" ,
						dataType : "json" ,
						headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json"
									}
						,success : function vcode(JSONData , status) {
							var json = JSONData.verifCode
						//알림 하나 띄우고 인증코드 입력 열기
						$("#verifDiv").show()
 						$("#inputVerification").val(json);
						alert("1"+  $("#inputVerification").val(json).val())
						swal("메일이 발송 되었습니다!", "작성하신 이메일을 확인해 주세요", "success");
						//Debug...
						}
					})//$.ajax 끝
					
					})//$("#emailcheck").on끝
					
		})//$(function)끝
	//////////////////////////////////인증 번호 확인
	$(function(){
		$("#verifBtn").on("click",function(){
			alert( $("inputVerification").val())
			alert($("#verification").val())
			if( $("#inputVerification").val() != $("#verification").val()){
				swal("인증번호가 다릅니다 다시 확인해 주세요!","헤헤 바보 :p","error")
			}else{
				swal("인증 되었습니다!","\\YAY/","success")
			}
		})
	})	
		
	 //////////////////////////////////가입 버튼
	 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			
			$("#addUserbtn").on("click" , function() {	
				
				if( $("#password").val() != $("#password2").val() ){
						swal("비밀번호가 다릅니다.","error");
						$("#password").val('')
						$("#password2").val('')
						//return;
				}
				
			
			$("form").attr("method" , "POST").attr("action" , "/libero/user/addUser").submit();
		
	})		
				
				
				//
			
		});	


    </script>
    
</head>
<body>
<div class="navbar  navbar-default">
        <div class="container">
        	<a class="navbar-brand" href="/index.jsp"></a>
   		</div>
   	</div>
   	<!-- ToolBar End /////////////////////////////////////-->

	<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
	
		<h1 class="bg-primary text-center">회 원 가 입</h1>
		
		<!-- form Start /////////////////////////////////////-->
		<form class="form-horizontal" enctype="multifile">
		
		<input 	type="hidden" name="type" >
		
		  <div class="form-group">
		    <label for="userId" class="col-sm-offset-1 col-sm-3 control-label">*아 이 디</label>
		    <div class="col-sm-4">
		      	<input type="text" class="form-control" 
		      		id="userId" name="userId" placeholder="아이디(이메일)" required="required">
		    </div>
		    <div class="col-sm-3" style="padding-top:5px;">
				<input type="button" id="emailcheck"  class="col" value="이메일 확인">
		    </div>
		  </div>
		  <input type="text" id="inputVerification" name ="inputVerification" hidden>
		  <div class="form-group" hidden id="verifDiv">
		  
		  	<label for="verification" class ="col-sm-offset-1 col-sm-3 control-label">인증 코드</label>
		  		<div class="col-sm-4">
		  			<input type="text" class="form-control" id="verification" name="verification" placeholder="인증 번호">
		  			
		  		</div>
		  		<div class="col-sm-3" style="padding-top:5px">
		  			<input type="button" id="verifBtn" class="col" value="확 인">
		  		</div>
		  </div>
		  
		  <div class="form-group">
		    <label for="password" class="col-sm-offset-1 col-sm-3 control-label">*비밀번호</label>
		    <div class="col-sm-4">
		      	<input type="password" class="form-control" 
		      		id="password" name="password" placeholder="비밀번호" required="required">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="password2" class="col-sm-offset-1 col-sm-3 control-label">*비밀번호 확인</label>
		    <div class="col-sm-4">
		      	<input type="password" class="form-control" 
		      		id="password2" name="password2" placeholder="비밀번호 확인" required="required">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="nickname" class="col-sm-offset-1 col-sm-3 control-label">닉네임</label>
		    <div class="col-sm-4">
		      	<input type="text" class="form-control" 
		      		id="nickname" name="nickname" placeholder="닉네임" >
		    </div>
		  </div>
		  
		  
		  <div class="form-group">
		    <label for="address" class="col-sm-offset-1 col-sm-3 control-label">주소</label>
		    <div class="col-sm-2">
				<input type="text" class="form-control" id="postCode" name="postCode" placeholder="우편번호">
		    </div>
			<div class="col-sm-3">
	      		<button type="button" onClick="daumjuso()" class="btn btn-info">우편번호 찾기</button>
	    	</div>
		   
		    <label for="address" class="col-sm-offset-1 col-sm-3 control-label"></label>
		    <div class="col-sm-5">
				<input type="text" id="address" name="address" placeholder="주소" size="50" class="form-control">
		    </div>
		    <label for="address" class="col-sm-offset-1 col-sm-3 control-label"></label>
		    <div class="col-sm-5">
				<input type="text" id="extraAddress" name="extraAddress" placeholder="상세주소" class="form-control">
		    </div>
		     <input type="hidden" name="address">
		 </div>
		  
		  <div class="form-group">
		    <label for="phone" class="col-sm-offset-1 col-sm-3 control-label">휴대전화번호</label>
		     <div class="col-sm-2">
		      <select class="form-control" name="phone1" id="phone1">
				  	<option value="010" >010</option>
					<option value="011" >011</option>
					<option value="016" >016</option>
					<option value="018" >018</option>
					<option value="019" >019</option>
				</select>
		    </div>
		    <div class="col-sm-2">
		      <input type="text" class="form-control" id="phone2" name="phone2" placeholder="번호">
		    </div>
		    <div class="col-sm-2">
		      <input type="text" class="form-control" id="phone3" name="phone3" placeholder="번호">
		    </div>
		 	
		  </div>
		  
		  <div class="form-group">
		  	<label for="hashtag"  class="col-sm-offset-1  col-sm-3 text-center control-label">해시태그</label>
		     <div class="col-sm-2">
		       <label><input type="checkbox" value="horror" id="horror" >공포</label>
		       <label><input type="checkbox" value="romance" id="romance">연애</label>
		       <label><input type="checkbox" value="thriller" id="thriller">스릴</label>
		       <label><input type="checkbox" value="action" id="action">액션</label>
		       <label><input type="checkbox" value="fantasy" id="fantasy">판타지</label>
		     </div>		  	
		  </div>
		  
		 <div class="form-group">
		    <label for="profile" class="col-sm-offset-1 col-sm-3 control-label">프로필 사진</label>
		    <div class="col-sm-4">
				<input type="file" id="profile" name="profile"  class="form-control">
		    </div>
		  </div>
		  
		  
		  <div class="form-group">
		    <div class="col-sm-offset-4  col-sm-4 text-center">
		      <button type="button" class="btn btn-primary" id="addUserbtn" >가 &nbsp;입</button>
			  <a class="btn btn-primary btn" href="#" role="button">취&nbsp;소</a>
		    </div>
		  </div>
		</form>
		<!-- form Start /////////////////////////////////////-->
		
 	</div>
	<!--  화면구성 div end /////////////////////////////////////-->
	




</body>
</html>