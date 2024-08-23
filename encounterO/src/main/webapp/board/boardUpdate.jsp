<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title> BOARD | EncounterO </title>
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
					<h2>BOARD</h2>
					<p>WRITE</p>
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
								<!-- appForm -->
						<form action="./BoardUpdateProAction.bo?directory=${param.directory}&bno=${param.bno}&pageNum=${param.pageNum}&pageSize=${param.pageSize}&contentNum=${param.contentNum}&count=${param.count}&criteria=${param.criteria}&keyWord=${param.keyWord}" method="post" class="upload">
							<fieldset>
								<legend>게시글 수정양식</legend>
								<ul class="form">
									<li class="clear">
										<label for="pwd_lbl" class="tit pilsoo_item">비밀번호</label>
										<div class="blank"><input type="password" name="pw" class="w100p" id="pwd_lbl" placeholder="로그인시 입력한 비밀번호를 입력해주세요"/></div>
									</li>
									<li class="clear">
										<label for="pwd_lbl" class="tit pilsoo_item">제목</label>
										<div class="blank"><input type="text" name="subject" class="w100p" id="pwd_lbl" value = "${oldDto.subject}"/></div>
									</li>
									<li class="clear">
									<label for="tit" class="tit_lbl">내용</label>
									<div class="blank"><textarea name="content" id="content_lbl" class="w100p">${oldDto.content}</textarea></div>
									</li>
								</ul>
								
								<input type="hidden" name="bno" value="${oldDto.bno }">
								
								<p class="btn_line" >
									<a><input type= "submit" value="수정하기" class="btn_basic"></a> 
									<a href="./Main.me"><input type= "button" value="메인으로" class="btn_basic"></a>
								</p>	
							</fieldset>
						</form>
						<!-- //appForm -->
		
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