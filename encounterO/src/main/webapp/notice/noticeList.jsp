<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title> NOTICE | EncounterO </title>
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
	
<div>
	<div id="container">
		<!-- intro_area -->
		<div class="intro_area">
		
				<div class="intro_area text">
					<h2>NOTICE</h2>
					<p>LIST</p>
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
				
				<table class="table">
					<caption class="hdd">게시글 목록</caption>
					
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:set var= "order" value= "0"/>
							<c:forEach var = "dto" items="${boardList }">
								<c:set var= "contentNum" value= "${count-((currentPage-1)*pageSize)-order}"/>
								<tr>
									<td>${contentNum}</td>
									<td><a href="./BoardContent.bo?directory=${dto.directory}&bno=${dto.bno}&pageNum=${(((count-contentNum)/pageSize)+1)-(((count-contentNum)/pageSize)+1)%1}&pageSize=${param.pageSize}&contentNum=${contentNum}&count=${count}">
									<c:forEach var = "i" begin = "1" end = "${dto.re_lev}" step = "1">
									↳  re:
									</c:forEach>
									${dto.subject } 
									<c:if test="${dto.repleNum!=0}">
									(${dto.repleNum})
									</c:if>
									</a></td>
									<td>${dto.name}</td>
									<td>${dto.readcount }회</td>
									<fmt:formatDate value="${dto.time}" pattern="yyyy.MM.dd HH:mm" var="time" />
									<td>${time}</td>
									<c:set var= "order" value= "${order+1}"/>
								</tr>
							</c:forEach>
						</tbody>
	
					
				</table>
				<!-- pagination -->
				<div class="pagination">
	
								<c:set var="prevPage" value="${param.pageNum-1}"/>	
								<c:if test="${prevPage>0}">
									<a href="./BoardList.bo?directory=${param.directory}&pageNum=1&pageSize=${pageSize}" class="firstpage  pbtn"><img src="./img/btn_firstpage.png" alt="첫 페이지로 이동"></a>
									<a href="./BoardList.bo?directory=${param.directory}&pageNum=${prevPage}&pageSize=${pageSize}" class="prevpage  pbtn"><img src="./img/btn_prevpage.png" alt="이전 페이지로 이동"></a>
								</c:if>
								
									<c:set var="left" value="1"/>
									<c:set var="addleft" value="0"/>
									
									<c:if test="${4>lastPage-pageNum}">
										<c:set var="addleft" value="${lastPage-pageNum-4}"/>
									</c:if>
									
									<c:if test="${pageNum>5}">
										<c:set var="left" value="${pageNum-4+addleft}"/>
									</c:if>
									
									<c:set var="right" value="${left+8}"/>
									
								<c:forEach var="i" begin="${left}" end="${right}" step="1">
									<c:if test="${1<=i&&i<=lastPage}">
										<a href="./BoardList.bo?directory=${param.directory}&pageNum=${i}&pageSize=${pageSize}">
										<span class="pagenum
											<c:if test="${i==currentPage}">
												currentpage
											</c:if>
										">${i}</span></a>	 
									</c:if>
								</c:forEach>
								
								<c:set var="nextPage" value="${pageNum+1}"/>
								<c:if test="${lastPage>=nextPage}">
									<a href="./BoardList.bo?directory=${param.directory}&pageNum=${nextPage}&pageSize=${pageSize}" class="nextpage  pbtn"><img src="./img/btn_nextpage.png" alt="다음 페이지로 이동"></a>
									<a href="./BoardList.bo?directory=${param.directory}&pageNum=${lastPage}&pageSize=${pageSize}" class="lastpage  pbtn"><img src="./img/btn_lastpage.png" alt="마지막 페이지로 이동"></a>
								</c:if>
	
				</div>
				<!-- //pagination -->
				
				<form action="#" class="minisrch_form">
					<fieldset>
						<legend>검색</legend>
						<input type="text" class="tbox" title="검색어를 입력해주세요" placeholder="검색어를 입력해주세요">
						<a href="javascript:;" class="btn_srch">검색</a>
					</fieldset>
				</form>
				
							<p class="btn_line" >
								<a href="./BoardWrite.bo?directory=${param.directory}&pageSize=${param.pageSize}"><input type= "button" value="글쓰기" class="btn_basic"></a>
								<a href="./Main.me"><input type= "button" value="메인으로" class="btn_basic"></a>
							</p>	
				
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