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
						${requestScope.name}�� �ȳ��ϼ���!
						</div>
						<div class = "right">
							<div class = "right_1">
								<a href="./MemberUpdate.me">Mypage</a>
							</div>
							<div class = "right_2">
								<a>Message : </a> 
								<a>${requestScope.chatAlarm}��</a>
							</div>
						</div>			
					</div>
					
					<c:if test="${sessionScope.myinfo.equals('opend')}">
					
					<div class="content">
						<table>
							<tr> 
								<td>ȸ����� : </td>
								<td>${requestScope.grade}</td>
							</tr>
							<tr> 
								<td>���� ��õȽ�� : </td>
								<td>${requestScope.token}ȸ</td>
							</tr>
							<tr> 
								<td>�湮Ƚ�� : </td>
								<td>${requestScope.visit}ȸ</td>
							</tr>
							<tr>
								<td><a class="" href="./BoardList.bo?directory=myPost&pageNum=1&pageSize=20">���� �ۼ��� �Խñ� : </a></td>
								<td><a class="" href="./BoardList.bo?directory=myPost&pageNum=1&pageSize=20"> ${requestScope.post}��</a></td>
							</tr>
							<tr> 
								<td><a class="" href="./BoardList.bo?directory=myReple&pageNum=1&pageSize=20">���� �ۼ��� ��� : </a></td>
								<td><a class="" href="./BoardList.bo?directory=myReple&pageNum=1&pageSize=20">${requestScope.replePost}��</a></td>
							</tr>
							<tr> 
								<td><a class="" href="./BoardList.bo?directory=repleForMe&pageNum=1&pageSize=20">���� �޸� ��� : </a></td>
								<td><a class="" href="./BoardList.bo?directory=repleForMe&pageNum=1&pageSize=20">${requestScope.repleAlarm}��</a></td>
							</tr>
							<tr> 
								<td><a class="" href="./BoardList.bo?directory=reWriteForMe&pageNum=1&pageSize=20">���� �޸� ��� : </a></td>
								<td><a class="" href="./BoardList.bo?directory=reWriteForMe&pageNum=1&pageSize=20">${requestScope.boardAlarm}��</a></td>
							</tr>
						</table>
					</div>
					
					</c:if>
					
					<div class="foot">
						<c:choose>
							<c:when test = "${sessionScope.myinfo.equals('opend')}">
								<form action="" method="post" >
									<input type="hidden" name="myinfo" value="closed">
					         	  	<button type="submit" class="opend">��</button>
		       					</form>
							</c:when>
							<c:otherwise>
								<form action="" method="post" >
									<input type="hidden" name="myinfo" value="opend">
					         	  	<button type="submit" class="closed">��</button>
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