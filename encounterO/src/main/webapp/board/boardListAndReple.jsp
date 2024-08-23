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
		
			<!-- bodytext_area -->
			<div class="bodytext_area box_center">
					<table class="table">
					<caption class="hdd">게시글 목록</caption>
					
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>추천수</th>
							<th>조회수</th>
							<th>작성일${count }</th>
						</tr>
					</thead>
					</table>
					
						<c:set var= "order" value= "0"/>
							<c:forEach var = "i" begin= "0" end = "${count-1 }" step = "1">
								<c:set var= "contentNum" value= "${count-((currentPage-1)*pageSize)-order}"/>
					<table class="table">
						<tbody>
								<tr>
									<td>${contentNum}</td>
									<td>
									<a class="${brList[i].bdto.likeCount >= 10 ? 'recomm' : ''}" href="./BoardContent.bo?directory=${brList[i].bdto.directory}&bno=${brList[i].bdto.bno}&pageNum=${(count-contentNum)/pageSize +1}&pageSize=${param.pageSize}&contentNum=${contentNum}&count=${count}&criteria=${param.criteria}&keyWord=${param.keyWord}">
											<c:forEach var = "j" begin = "0" end = "${brList[i].bdto.re_lev}" step = "1">
												↳  re:
											</c:forEach>
										${brList[i].bdto.subject} 
											<c:if test="${brList[i].bdto.repleNum!=0}">
												 (${brList[i].bdto.repleNum})
											</c:if>
										</a>
									</td>
									<td>${brList[i].bdto.name}</td>
									<td>${brList[i].bdto.likeCount }</td>
									<td>${brList[i].bdto.readCount }</td>
									<fmt:formatDate value="${brList[i].bdto.time}" pattern="yyyy.MM.dd HH:mm" var="b_time" />
									<td>${b_time } </td>
									<c:set var= "order" value= "${order+1}"/>
								</tr>
						</tbody>
					</table>
								
								<c:forEach var = "rdto" items="${brList[i].rdtoList }">
									<a href="./BoardContent.bo?directory=${brList[i].bdto.directory}&bno=${brList[i].bdto.bno}&pageNum=${(count-contentNum)/pageSize +1}&pageSize=${param.pageSize}&contentNum=${contentNum}&count=${count}&criteria=${param.criteria}&keyWord=${param.keyWord}">
										<div class = "reple">	<!-- reple -->
											<div class = "reader"> 	<!-- reple reader -->
												<div class = "contents">
													<ul>			
														<li class = "name">${rdto.name }</li>
														<fmt:formatDate value="${rdto.time}" pattern="yyyy.MM.dd HH:mm" var="r_time" />
														<li class = "date">${r_time }</li>
													</ul>
													<ul>
														<li class = "reple_body">${rdto.content }</li>
													</ul>	
												</div>
												<div class = "editor">
												</div>
											</div>	<!-- reple reader -->
										</div>
									</a>
									
								</c:forEach>
								
							</c:forEach>
							
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
					        <option value="name">게시글 회원명</option>
					        <option value="id">게시글 아이디</option>
					        <option value="subject">게시글 제목</option>
					        <option value="content">게시글 내용</option>
					        <option value="repleContent">댓글 내용</option>
					    </select>
						<input type="text" class="tbox" name="keyWord" title="검색어를 입력해주세요" placeholder="검색어를 입력해주세요">
						<input type="submit" class="btn_srch" value="검색"></input>
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
			
			<!-- box_right -->
			<div class= "box_right">
			</div>
			<!-- box_light -->
			
		</div>
		<!-- box_total -->
	</div>
	<!-- //container -->


</div>
<!-- //wrap -->


</body>
</html>