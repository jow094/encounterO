<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>start.jsp</h1>
	<%
		// MVC 프로젝트의 시작지점
		// 원하는 주소로 페이지 이동
		// ** MVC 프로젝트의 가장 큰 목적은 start.jsp를 제외하고는 절대로 jsp 파일을 실행하지 않게 만드는 것임.
		// ** 주소줄에도 .jsp 주소는 표시되지 않고 .me 라는 가상의 주소로 실행된다.
		
		//response.sendRedirect("http://localhost:8088/MVCV/test.me");
		response.sendRedirect("./Main.me");
		
		//작업순서
		// 1. start.jsp 생성하여 ./Main.me 로 이동하게 함
		// 2. web.xml 파일에서 매핑체크, 어디로 갈지 확인
		// 3. MemberFrontController로 이동하여 doProcess(doGet/doPost) 실행. 
		//		doProcess는 다음 세가지 동작을 함. 
		//			1. 가상주소 계산
		//			2. 가상주소 매핑
		//				2-1) 패턴1 : DB처리 없이 페이지 이동
		//				2-2) 패턴2 : DB처리를 하고서 페이지 이동, DB처리 과정에서 MemberJoinAction 객체가 필요하여 생성, 전달정보 저장
		//							 이후 MemberDTO를 MemberDAO 객체를 통해 처리
		//
		//3. 가상주소 이동}
		//
		//
	%>
</body>
</html>