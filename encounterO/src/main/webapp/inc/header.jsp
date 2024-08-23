<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 	<header id="header">
  	  <a href="./Main.me" id="logo_area"></a>

		<div class="box_inner clear">	
			<!-- header_cont -->
			<div class="header_cont">
				<ul class="util clear">
					<c:if test="${!empty sessionScope.id}" >
						<li> ${sessionScope.name}님 안녕하세요!</li>
					</c:if>
					<c:if test="${empty sessionScope.id}" >
						<li><a href="./Login.me">로그인</a></li>
					</c:if>
					<c:if test="${empty sessionScope.id}" >
						<li><a href="./MemberJoin.me">회원가입</a></li>
					</c:if>
					<c:if test="${!empty sessionScope.id}" >
						<li><a href="./Logout.me">로그아웃</a></li>
					</c:if>
					<c:if test="${!empty sessionScope.id && !sessionScope.id.equals('admin')}" >
						<li><a href="./MemberUpdate.me">마이페이지</a></li>
					</c:if>
					<c:if test="${!empty sessionScope.id && sessionScope.id.equals('admin')}" >
						<li><a href="./MemberList.me">회원관리</a></li>
					</c:if>
					
				</ul>	
				<nav>
				<ul class="gnb clear">
					<li><a href="" class="openAll1">PROFILE</a>
                        <div class="gnb_depth gnb_depth2_1">
                            <ul class="submenu_list">
                                <li><a href="">. Jo</a></li>
                                <li><a href="">. Page</a></li>
                            </ul>
                        </div>
					</li>
					<li><a href="" class="openAll2">PORTFOLIO</a>
				        <div class="gnb_depth gnb_depth2_2">
                            <ul class="submenu_list">
                                <li><a href="">. Output</a></li>
                                <li><a href="./BoardFileWrite.bo">. Src</a></li>
                            </ul>
                        </div>
					</li>
					<li><a href="./BoardList.bo?directory=comm&pageNum=1&pageSize=20" class="openAll3">BOARD</a>
                        <div class="gnb_depth gnb_depth2_3">
                            <ul class="submenu_list">
                                <li><a href="./BoardList.bo?directory=comm&pageNum=1&pageSize=20">. Community</a></li>
                                <li><a href="./BoardList.bo?directory=diary&pageNum=1&pageSize=20">. Diary</a></li>
                            </ul>
                        </div>
					</li>
					<li><a href="./NoticeList.no?pageNum=1" class="openAll4">NOTICE</a>
						<div class="gnb_depth gnb_depth2_4">
                        </div>
					</li>
				</ul>
                </nav>
			</div>
			<!-- //header_cont -->
		</div>
	</header>