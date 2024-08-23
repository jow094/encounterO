<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
					<p>CONTENTS</p>
				</div>	
				
				<div class="intro_area img">
				</div>	
				
		</div>	
		<!-- intro_area -->
		
		
		<!-- box_total -->
		<div class = "box_total">
		<!-- box_left -->
		<div class= "box_left">
		
		
		</div>
		<!-- box_left -->

		<!-- bodytext_area -->
		<div class="bodytext_area box_center">			
			<div class="contents">
				<ul class="contents_reader">			
					<li class="contents_num">${param.contentNum}번</li>
					<li class="contents_title">${dto.subject}</li>
				</ul>
				<ul class="contents_reader">	
					<li class="contents_info_first_left">작성자 : ${dto.name }</li>
						<fmt:formatDate value="${dto.time}" pattern="yyyy-MM-dd HH:mm" var="time" />
					<li class="contents_info_first_right">작성일 : ${time}</li>
				</ul>
				<ul class="contents_reader">
					<li class="contents_info_second_left">추천수 : 0회</li>
					<li class="contents_info_second_right">조회수 : ${dto.readcount}회</li>
				</ul>	
				
				<ul class="contents_reader">
					<li class="contents_content">
							<div class="editer_content">
							${dto.content }
		                    </div>
					</li>
				</ul>	
			</div>
			
			<p class="btn_line txt_right">
				<a href="" class="btn_basic">추천</a>
					<c:if test="${dto.id==sessionScope.id||sessionScope.id=='admin'}" >
						<a href="./BoardUpdate.bo?directory=${dto.directory}&bno=${dto.bno}&pageNum=${param.pageNum}&pageSize=${param.pageSize}&contentNum=${param.contentNum}&count=${param.count}" class="btn_basic">수정</a>
						<a href="./BoardDelete.bo?directory=${dto.directory}&bno=${dto.bno}&pageNum=${param.pageNum}&pageSize=${param.pageSize}" class="btn_basic">삭제</a>
					</c:if>
				
					<c:if test="${sessionScope.id!=null}" >
						<a href="./BoardReWrite.bo?directory=${dto.directory}&bno=${dto.bno}&pageNum=${param.pageNum}&pageSize=${param.pageSize}&contentNum=${param.contentNum}&count=${param.count}&re_ref=${dto.re_ref}&re_lev=${dto.re_lev}&re_seq=${dto.re_seq}" class="btn_basic">답글</a>
					</c:if>
				
				<a href="./BoardList.bo?directory=${dto.directory}&pageNum=${param.pageNum}&pageSize=${param.pageSize}" class="btn_basic">목록</a>
			</p>
			
			<div class = "reple">	<!-- reple -->
			
				<c:forEach var = "rdto" items="${rdto}">
					<div class = "reader"> 	<!-- reple reader -->
						<div class = "contents">
							<ul>			
								<li class = "name">${rdto.name }</li>
								<li class = "date">${rdto.time }</li>
							</ul>
							<ul>
								<li class = "reple_body">${rdto.content }</li>
							</ul>	
						</div>
						<div class = "editor">
						</div>
					</div>	<!-- reple reader -->
				</c:forEach>
				
				<div class = "writer"> 	<!-- reple writer -->
					<c:choose>
						<c:when test="${sessionScope.id!=null}" >
							<form action="./RepleWriteAction.bo?directory=${dto.directory}&bno=${dto.bno}&pageNum=${param.pageNum}&pageSize=${param.pageSize}&contentNum=${param.contentNum}&count=${param.count}" method="post" class="appForm">
								<ul class="app_list">
									<li class="clear">
										<div class="app_content"><input type="text" name="content" class="w100p" id="pwd_lbl" placeholder="댓글을 입력해주세요."/></div>
									</li>
								</ul>
								<p class="btn_line txt_right" >
									<a><input type= "submit" value="댓글 달기" class="btn_basic"></a> 
								</p>	
							</form>
						</c:when>
						
						<c:otherwise>
								<form action="" method="post" class="appForm">
								<ul class="app_list">
									<li class="clear">
										<label for="pwd_lbl" class="tit_lbl">댓글</label>
										<div class="app_content"><input type="text" name="subject" class="w100p" id="pwd_lbl" placeholder="댓글은 로그인 후에만 작성할 수 있습니다."/></div>
									</li>
								</ul>
							</form>
						</c:otherwise>
					</c:choose>
				</div>		<!-- reple writer -->
				
			</div> 	<!-- reple -->
							
			<ul class="near_list mt20">
				<li>
						<h4 class="prev">다음글</h4>
						
						<c:choose>				
						<c:when test="${dto.nextSub != null}" >
						<a href="./BoardContent.bo?bno=${dto.nextBno}&pageNum=${(((param.count-param.contentNum+1)/10)+1)-(((param.count-param.contentNum+1)/10)+1)%1}&pageNum=${param.pageSize}&contentNum=${param.contentNum+1}&count=${param.count}">
						${dto.nextSub}
						</a>
						</c:when>
						<c:otherwise>
						<p>해당 게시글은 마지막 게시글입니다.</p>
						</c:otherwise>
						</c:choose>	
				</li>
				
				<li>
						<h4 class="next">이전글</h4>
						
						<c:choose>				
						<c:when test="${dto.prevSub != null}" >
						<a href="./BoardContent.bo?bno=${dto.prevBno}&pageNum=${(((param.count-param.contentNum+1)/10)+1)-(((param.count-param.contentNum+1)/10)+1)%1}&pageNum=${param.pageSize}&contentNum=${param.contentNum-1}&count=${param.count}">
						${dto.prevSub}
						</a>
						</c:when>
						<c:otherwise>
						<p>해당 게시글은 첫번째 게시글입니다.</p>
						</c:otherwise>
						</c:choose>	
				</li>
				
			</ul>
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