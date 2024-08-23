<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title> 홈 | EncounterO </title>
<link rel="icon" href="./img/favicon.png" type="image/png">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="./css/swiper.min.css">
<link rel="stylesheet" href="./css/common.css">
<script src="./js/jquery-1.11.3.min.js"></script>
<script src="./js/rollmain.js"></script>
<script src="./js/jquery.easing.js"></script>	
<script src="./js/common.js"></script>  
<script src="./js/jquery.smooth-scroll.min.js"></script> 

<!--[if lte IE 9]>
    <script src="js/html5shiv.js"></script>
	<script src="js/placeholders.min.js"></script>
<![endif]-->
</head>
		<jsp:include page="../inc/header.jsp"></jsp:include>
		
<body>

	<div id="main_container">
        <div class="main_rolling_pc">
            <div class="visualRoll">
                <ul class="viewImgList">
                    <li class="imglist0">
                        <div class="roll_content">
                            <a href="javascript:;">
                            <img src=""></a> <!-- slidecontendts -->
                            <p class="roll_txtline"><p> <!-- 사진 1 문구 -->
                        </div>
                    </li>
                    <li class="imglist1">
                        <div class="roll_content">
                            <a href="javascript:;">
                            <img src=""></a> <!-- slidecontendts -->
                            <p class="roll_txtline"></p> <!-- 사진 2 문구 -->
                        </div>
                    </li>
                    <li class="imglist2">
                        <div class="roll_content">
                            <a href="javascript:;">
                            <img src=""></a> <!-- ./img/img_slidecontents03.png -->
                            <p class="roll_txtline"></p> <!-- 사진 3 문구 -->
                        </div>
                    </li>
                </ul>

                <div class="rollbtnArea">
                    <ul class="rollingbtn">
                        <li class="seq butt0"><a href="#butt"><img src="./img/btn_rollbutt_on.png" alt="1번" /></a></li>
                        <li class="seq butt1"><a href="#butt"><img src="./img/btn_rollbutt_off.png" alt="2번" /></a></li>
                        <li class="seq butt2"><a href="#butt"><img src="./img/btn_rollbutt_off.png" alt="3번" /></a></li>
                        <li class="rollstop"><a href="#" class="stop"><img src="./img/btn_roll_stop.png" alt="멈춤" /></a></li>
                        <li class="rollplay"><a href="#" class="play"><img src="./img/btn_roll_play.png" alt="재생" /></a></li>
                    </ul>
                </div><!-- //rollbtnArea -->

            </div><!-- //visualRoll -->
        </div><!-- //main_rolling_pc -->
	</div>
	<!-- //container -->
		<jsp:include page="../inc/footer.jsp"></jsp:include>

<!-- //wrap -->

<div class="quick_area">
 	<ul class="quick_list">
		<li><a href="./Main.me"><p>메인으로</p></a></li>
	</ul>
	<p class="to_top"><a href="#layout0" class="s_point">TOP</a></p>
</div>

<script type="text/javascript" src="./js/swiper.min.js"></script>
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