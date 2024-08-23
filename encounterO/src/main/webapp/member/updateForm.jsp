<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
					<p>UPDATE</p>
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
						<form action="./MemberUpdateProAction.me" class="info">
							<input type="hidden" name = "oldURL" value="<%=request.getParameter("oldURL")%>">	
							<input type="hidden" name = "prevEmail1" value="${dto.email1}">	
							<input type="hidden" name = "prevEmail2" value="${dto.email2}">	
							<fieldset class="info">
								<legend>정보수정양식</legend>
								
								<ul class="form">
									<li class="clear">
										<div class="tit">아이디</div>
										<div class="blank"><input type="text" name="id" value="${dto.id}" readonly/></div>
									</li>
									<li class="clear">
										<div class="tit must">비밀번호</div>
										<div class="blank"><input type="password" name="pw" placeholder="현재 비밀번호를 입력하세요."/></div>
									</li>
									<li class="clear">
										<div class="tit must">회원명</div>
										<div class="blank"><input type="text" name="name" value="${dto.name}"/></div>
									</li>
									<li class="clear">
										<span class="tit must" >성별</span>
										<div class="blank">
											<input type="radio" name="gender" value="남성" ${dto.gender.equals('남성')? 'checked':''}/>
											<label for="radio">남성</label>
											<input type="radio" name="gender" value="여성" ${dto.gender.equals('여성')? 'checked':''}>
											<label for="radio">여성</label>
										</div>
									</li>
									<li class="clear">
										<div class="tit must">나이</div>
										<div class="blank"><input type="number" name="age" value="${dto.age}"/></div>
									</li>
									<li class="clear">
										<div class="tit must">휴대폰</div>
										<div class="blank"><input type="tel" name="phone" value="${dto.phone}"/></div>
									</li>
									<li class="clear">
			                            <div class="tit must">이메일</div>
			                            <div class="blank">
			                                <input type="email" name="email1" value="${dto.email1}" />
			                               @
			                                <input type="email" name="email2" value="${dto.email2}" />										
			                            </div>
									</li>
									
								</ul>
									<p class="btn_line" >
										<a><input type= "submit" value="정보수정" class="btn_basic"></a> 
										<a href="./Main.me"><input type= "button" value="메인으로" class="btn_basic"></a>
										<a href="./MemberDelete.me"><input type= "button" value="회원탈퇴" class="btn_basic"></a>
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