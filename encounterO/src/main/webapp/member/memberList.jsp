<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>/member/memberList.jsp</h1>
	
	<h2>관리자 전용 페이지</h2>
	<!-- 페이지에 접근한 사용자가 관리자가 아닌 경우 페이지 이동(메인페이지) -->
	<%
		String id = (String)session.getAttribute("id");
		if(id==null||!id.equals("admin")){
			response.sendRedirect("main.jsp");
		}
	%>
	
	<h2>관리자 메뉴 - 회원목록 조회</h2>
	
		<table border="1">
			<tr>
				<td>아이디</td>
				<td>비밀번호</td>
				<td>이름</td>
				<td>성별</td>
				<td>나이</td>
				<td>이메일</td>
				<td>가입일</td>
			</tr>
			<c:forEach var="m" items="${memberList}" >
			<tr>
				<td>${m.id}</td>
				<td>${m.pw}</td>
				<td>${m.name}</td>
				<td>${m.gender}</td>
				<td>${m.age}</td>
				<td>${m.email1}@${m.email2 }</td>
				<td>
					<fmt:formatDate value = "${m.regdate}" type = "both"/>
				</td>
			</tr>
			</c:forEach>
		</table>
	
		<h3><a href="./Main.me"> 메인페이지로 이동 </a></h3>
	
	
</body>
</html>