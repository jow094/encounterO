<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

	<%
	if (request.getMethod().equalsIgnoreCase("POST")) {
    String myinfo = request.getParameter("myinfo");
    
		if (myinfo.equals("closed")) {
	        session.setAttribute("myinfo", "closed");
	    }else if(myinfo.equals("opend")){
	        session.setAttribute("myinfo", "opend");
	    }else{
	        session.setAttribute("myinfo", "opend");
	    }
	}
    %>
    
	<div class = "left_side">
		<div class = "left_side_top">
			<c:if test="${sessionScope.id!=null}">
				
				<div class = "${sessionScope.myinfo.equals('opend')?'left_side_opend':'left_side_closed'}">
					<div class = "controller">
						<div class = "left">
						${requestScope.name}님 안녕하세요!
						</div>
						<div class = "right">
							<div class = "right_1">
								<a href="./MemberUpdate.me">Mypage</a>
							</div>
							<div class = "right_2">
								<a>Message : </a> 
								<a>${requestScope.chatAlarm}개</a>
							</div>
						</div>			
					</div>
					
					<c:if test="${sessionScope.myinfo.equals('opend')}">
					
					<div class="content">
						<table>
							<tr> 
								<td>회원등급 : </td>
								<td>${requestScope.grade}</td>
							</tr>
							<tr> 
								<td>남은 추천횟수 : </td>
								<td>${requestScope.token}회</td>
							</tr>
							<tr> 
								<td>방문횟수 : </td>
								<td>${requestScope.visit}회</td>
							</tr>
							<tr>
								<td><a class="" href="./BoardList.bo?directory=myPost&pageNum=1&pageSize=20">내가 작성한 게시글 : </a></td>
								<td><a class="" href="./BoardList.bo?directory=myPost&pageNum=1&pageSize=20"> ${requestScope.post}개</a></td>
							</tr>
							<tr> 
								<td><a class="" href="./BoardList.bo?directory=myReple&pageNum=1&pageSize=20">내가 작성한 댓글 : </a></td>
								<td><a class="" href="./BoardList.bo?directory=myReple&pageNum=1&pageSize=20">${requestScope.replePost}개</a></td>
							</tr>
							<tr> 
								<td><a class="" href="./BoardList.bo?directory=repleForMe&pageNum=1&pageSize=20">새로 달린 댓글 : </a></td>
								<td><a class="" href="./BoardList.bo?directory=repleForMe&pageNum=1&pageSize=20">${requestScope.repleAlarm}개</a></td>
							</tr>
							<tr> 
								<td><a class="" href="./BoardList.bo?directory=reWriteForMe&pageNum=1&pageSize=20">새로 달린 답글 : </a></td>
								<td><a class="" href="./BoardList.bo?directory=reWriteForMe&pageNum=1&pageSize=20">${requestScope.boardAlarm}개</a></td>
							</tr>
						</table>
					</div>
					
					</c:if>
					
					<div class="foot">
						<c:choose>
							<c:when test = "${sessionScope.myinfo.equals('opend')}">
								<form action="" method="post" >
									<input type="hidden" name="myinfo" value="closed">
					         	  	<button type="submit" class="opend">　</button>
		       					</form>
							</c:when>
							<c:otherwise>
								<form action="" method="post" >
									<input type="hidden" name="myinfo" value="opend">
					         	  	<button type="submit" class="closed">　</button>
		       					</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:if>
		</div> <!-- left_side_top -->	
	
		<div class ="left_side_bottom">
				<div class ="chat_content">
				
				
				</div>
				<div class ="chat_input">
					<form action="" method="post" >
						<input type="text" name="chat">
    				</form>
				</div>
		</div> <!-- left_side_bottom -->	
	</div> <!-- left_side -->

</body>
</html>