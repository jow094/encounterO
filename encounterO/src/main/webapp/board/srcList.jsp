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
	
<div>
	<div id="container">
		<!-- intro_area -->
		<div class="intro_area">
		
				<div class="intro_area text">
					<h2>BOARD</h2>
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
			<jsp:include page="../inc/left_side.jsp"></jsp:include>
			</div>
			<!-- box_left -->
		
		<div class = "box_center">
			<!-- bodytext_area -->
			<div class="bodytext_area box_center">
				
				<table class="list_table">
					<caption class="hdd">게시글 목록</caption>
					
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>추천수</th>
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
									<td>
										<a class="${dto.likeCount >= 10 ? 'recomm' : ''}" href="./BoardContent.bo?directory=${param.directory}&bno=${dto.bno}&pageNum=${(count-contentNum)/pageSize +1}&pageSize=${param.pageSize}&contentNum=${contentNum}&count=${count}&criteria=${param.criteria}&keyWord=${param.keyWord}">
											<c:forEach var = "i" begin = "1" end = "${dto.re_lev}" step = "1">
												↳  re:
												<!-- <img src="./img/level.gif" width="${dto.re_lev * 10}">
												<img src="./img/re.gif"> -->
											</c:forEach>
										${dto.subject} 
											<c:if test="${dto.repleNum!=0}">
												 (${dto.repleNum})
											</c:if>
										</a>
									</td>
									<td>${dto.name}</td>
									<td>${dto.likeCount }회</td>
									<td>${dto.readCount }회</td>
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
								<a href="./BoardList.bo?directory=${param.directory}&pageNum=1&pageSize=${pageSize}&criteria=${param.criteria}&keyWord=${param.keyWord}" class="firstpage  pbtn"><img src="./img/btn_firstpage.png" alt="첫 페이지로 이동"></a>
								<a href="./BoardList.bo?directory=${param.directory}&pageNum=${prevPage}&pageSize=${pageSize}&criteria=${param.criteria}&keyWord=${param.keyWord}" class="prevpage  pbtn"><img src="./img/btn_prevpage.png" alt="이전 페이지로 이동"></a>
									
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
										<a href="./BoardList.bo?directory=${param.directory}&pageNum=${i}&pageSize=${pageSize}&criteria=${param.criteria}&keyWord=${param.keyWord}">
											<span class="pagenum
												<c:if test="${i==currentPage}">
													currentpage
												</c:if>
											">${i}
											</span>
										</a>
									</c:if>
									
								</c:forEach>
								
								<c:set var="nextPage" value="${pageNum+1}"/>
							
								<c:if test="${lastPage>=nextPage}">
											<a href="./BoardList.bo?directory=${param.directory}&pageNum=${nextPage}&pageSize=${pageSize}&criteria=${param.criteria}&keyWord=${param.keyWord}" class="nextpage  pbtn"><img src="./img/btn_nextpage.png" alt="다음 페이지로 이동"></a>
											<a href="./BoardList.bo?directory=${param.directory}&pageNum=${lastPage}&pageSize=${pageSize}&criteria=${param.criteria}&keyWord=${param.keyWord}" class="lastpage  pbtn"><img src="./img/btn_lastpage.png" alt="마지막 페이지로 이동"></a>
								</c:if>
	
				</div>
				<!-- //pagination -->
				
				<form action="./BoardList.bo" class="minisrch_form">
					<fieldset>
					<input type="hidden" name="directory" value=${param.directory }></input>
					<input type="hidden" name="pageNum" value=${param.pageNum }></input>
					<input type="hidden" name="pageSize" value=${param.pageSize }></input>
						<legend>검색</legend>
						<select name="criteria">
					        <option value="name">회원명</option>
					        <option value="id">아이디</option>
					        <option value="subject">제목</option>
					        <option value="content">내용</option>
					    </select>
						<input type="text" class="tbox" name="keyWord" title="검색어를 입력해주세요" placeholder="검색어를 입력해주세요">
						<input type="submit" class="btn_basic" value="검색"></input>
					</fieldset>
				</form>
				<form action="./BoardList.bo" class="minisrch_form">
					<input type="hidden" name="directory" value=${param.directory }></input>
					<input type="hidden" name="pageNum" value=1></input>
					<select name="pageSize" onchange="this.form.submit()">
				       <option value="" disabled selected>${param.pageSize } 개씩 보기</option>
						<c:if test="${param.pageSize==20}">
							<option value="40">40개씩 보기</option>
					        <option value="60">60개씩 보기</option>
						</c:if>
						<c:if test="${param.pageSize==40}">
							<option value="20">20개씩 보기</option>
					        <option value="60">60개씩 보기</option>
						</c:if>
						
						<c:if test="${param.pageSize==60}">
							<option value="20">20개씩 보기</option>
					        <option value="40">40개씩 보기</option>
						</c:if>
					</select>
					<input type="hidden" name="criteria" value=${param.criteria }></input>
					<input type="hidden" name="keyWord" value=${param.KeyWord }></input>
				</form>
							<p class="btn_line" >
								<a href="./BoardWrite.bo?directory=${param.directory}&pageSize=${param.pageSize}"><input type= "button" value="글쓰기" class="btn_basic"></a>
								<a href="./Main.me"><input type= "button" value="메인으로" class="btn_basic"></a>
							</p>	
			</div>
			<!-- //bodytext_area -->
			</div>
			<!-- box_center -->
			
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