<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title> MEMBER  | EncounterO </title>
<link rel="icon" href="./img/favicon.png" type="image/png">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="./css/common.css">
<script src="./js/jquery-1.11.3.min.js"></script>
<script src="./js/common.js"></script>  
<script src="./js/jquery.smooth-scroll.min.js"></script> 
<!--[if lte IE 9]>
    <script src="js/html5shiv.js"></script>
	<script src="js/placeholders.min.js"></script>
<![endif]-->
</head>

<body>

	<jsp:include page="../inc/header.jsp"></jsp:include>
	
	<div id="container">
	
		<!-- intro_area -->
		<div class="intro_area">
		
				<div class="intro_area text">
					<h2>MEMBER</h2>
					<p>JOIN</p>
				</div>	
				
				<div class="intro_area img">
				</div>	
				
		</div>	
		<!-- intro_area -->
		<!-- box_total -->
		<div class = "box_total">
			<!-- box_left -->
			<div class= "box_left">
				<jsp:include page="../inc/left_side.jsp"></jsp:include>
			</div>
			<!-- box_left -->
			
					<!-- bodytext_area -->
					<div class="bodytext_area box_center">
						<form action="./MemberJoinAction.me" method="post" class="info">
							<input type="hidden" name = "oldURL" value="<%=request.getParameter("oldURL")%>">	
							<fieldset class = "info">
								<legend>회원가입 양식</legend>
								
								<ul class="form">
									<li class="clear">
										<div class="tit must">아이디</div>
										<div class="blank"><input type="text" name="id" placeholder="아이디는 4자리 이상, 15자리 이하의 영문 및 숫자 조합이어야 합니다."/></div>
									</li>
									<li class="clear">
										<div class="tit must">비밀번호</div>
										<div class="blank"><input type="password" name="pw" placeholder="비밀번호는 6자리 이상, 20자리 이하여야 합니다."/></div>
									</li>
									<li class="clear">
										<div class="tit must">비밀번호 확인</div>
										<div class="blank"><input type="password" name="pw2" placeholder="비밀번호를 다시 한번 입력해주세요."/></div>
									</li>
									<li class="clear">
										<div class="tit must">회원명</div>
										<div class="blank"><input type="text" name="name" placeholder="회원명은 2자리 이상, 10자리 이하여야 합니다."/></div>
									</li>
									<li class="clear">
										<div class="tit must">성별</div>
										<div class="blank">
											<input type="radio" name="gender" value="남성" checked><label for="radio">남</label>
											<input type="radio" name="gender" value="여성"><label for="radio">여</label>
										</div>
									</li>
									<li class="clear">
										<div class="tit must">나이</div>
										<div class="blank"><input type="number" name="age" placeholder="14세 이상, 65세 미만만 가입 가능합니다."/></div>
									</li>
									<li class="clear">
										<div class="tit must">휴대폰</div>
										<div class="blank"><input type="tel" name="phone" placeholder="휴대폰 번호를 ”-”없이 숫자만 입력하세요."/></div>
									</li>
									<li class="clear">
			                           <div class="tit must">이메일</div>
			                            <div class="blank">
			                                <input type="email" name="email1" placeholder="ID" />
			                                @
			                                <input type="email" name="email2" placeholder="email.com" />										
			                            </div>
									</li>
									
								</ul>
									<p class="btn_line" >
										<a><input type= "submit" value="회원가입" class="btn_basic"></a> 
										<a href="./Login.me"><input type= "button" value="로그인" class="btn_basic"></a>
										<a href="./Main.me"><input type= "button" value="메인으로" class="btn_basic"></a>
									</p>	
							</fieldset>
						</form>
					</div>
					<!-- //bodytext_area -->
									
					
			<!-- box_right -->
			<div class= "box_right">
			</div>
			<!-- box_light -->
			
		</div>
		<!-- box_total -->
	</div>
	<!-- //container -->
		

	<jsp:include page="../inc/footer.jsp"></jsp:include>


<div class="quick_area">
 	<ul class="quick_list">
		<li><a href="./Main.me"><p>메인으로</p></a></li>
	</ul>
	<p class="to_top"><a href="#layout0" class="s_point">TOP</a></p>
</div>

<script type="text/javascript" src="../js/swiper.min.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
		var swiper = new Swiper('.swiper-container', {
			loop: true,
			autoplay:5500,
		    autoplayDisableOnInteraction: false,
			pagination: '.swiper-pagination',
            paginationClickable: true
		});
	});
</script>


</body>
</html>