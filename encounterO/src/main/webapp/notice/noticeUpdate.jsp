<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title> 공지사항 | EncounterO </title>
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
<ul class="skipnavi">
    <li><a href="#container">본문내용</a></li>
</ul>
<!-- wrap -->
<div id="wrap">

	<jsp:include page="../inc/header.jsp"></jsp:include>
	
	<div id="container">
	
			<!-- location_area -->
		<div class="location_area notice">
			<div class="box_inner">
				<h2 class="tit_page">EncounterO</h2>
				<p class="location">NOTICE <span class="path">/</span> 글쓰기</p>
				<ul class="page_menu clear">
					<li><a href="javascript:;" class="on">글쓰기</a></li>
				</ul>
			</div>
			
			
		</div>	
		<!-- //location_area -->
		
		<!-- bodytext_area -->
			<div class="bodytext_area box_inner">
						<!-- appForm -->
				<form action="./NoticeUpdateProAction.no?bno=${param.bno}&pageNum=${param.pageNum}&contentNum=${param.contentNum}&count=${param.count}" method="post" class="appForm">
					<fieldset>
						<legend>게시글 작성양식</legend>
						<ul class="app_list">
							<li class="clear">
								<label for="pwd_lbl" class="tit_lbl pilsoo_item">비밀번호</label>
								<div class="app_content"><input type="password" name="pass" class="w100p" id="pwd_lbl" placeholder="로그인시 입력한 비밀번호를 입력해주세요"
								/></div>
							</li>
							<li class="clear">
								<label for="pwd_lbl" class="tit_lbl pilsoo_item">제목</label>
								<div class="app_content"><input type="text" name="subject" class="w100p" id="pwd_lbl" value = "${oldDto.subject}"/></div>
							</li>
							<li class="clear">
							<label for="content_lbl" class="tit_lbl">내용</label>
							<div class="app_content"><textarea name="content" id="content_lbl" class="w100p">${oldDto.content}</textarea></div>
							</li>
						</ul>
						
						<input type="hidden" name="bno" value="${oldDto.bno }">
						
						<p class="btn_line" >
							<a><input type= "submit" value="수정하기" class="btn_baseColor"></a> 
							<a href="./Main.me"><input type= "button" value="메인으로" class="btn_baseColor"></a>
						</p>	
					</fieldset>
				</form>
				<!-- //appForm -->

		</div>
		<!-- //bodytext_area -->
			
		
		

	</div>
	<!-- //container -->

		<jsp:include page="../inc/footer.jsp"></jsp:include>

</div>
<!-- //wrap -->

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