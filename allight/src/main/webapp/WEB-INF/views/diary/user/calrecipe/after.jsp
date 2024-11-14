<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
$(function(){
	$('#check').click(function(){
		$("#check").attr('href','${pageContext.request.contextPath}/recipeRe.com');
	});
})
</script>
</head>
<body>
	<div id="wrap2">
		<div class="title2">칼로리 처방</div>
		<table class="table" style="margin-bottom:40px;">
			<tr>
				<th width="30%" style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">총 감량기간</th>
				<td style="padding:25px;border-bottom:0;">${LIST.crterm}<c:if test="${empty sessionScope.MID}">${LIST.type}</c:if></td>
			</tr>
			<tr>
				<th style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">현제 체중</th>
				<td style="padding:25px;border-bottom:0;">${LIST.crweight}kg</td>
			</tr>
			<tr>
				<th style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">목표 체중</th>
				<td style="padding:25px;border-bottom:0;">${LIST.crgoalweight}kg</td>
			</tr>
			<tr>
				<th style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">BMI</th>
				<td style="padding:25px;border-bottom:0;">${LIST.crbmi}</td>
			</tr>
			<tr>
				<th style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">평소 활동량</th>
				<td style="padding:25px;border-bottom:0;"><c:if test="${LIST.cractive ==1}">활동안함 (운동을 전혀 안 해요.)</c:if>
					<c:if test="${LIST.cractive ==2}">가벼운 활동 (평소 가벼운 운동이나 스포츠를 즐겨요)</c:if>
					<c:if test="${LIST.cractive ==3}">보통 활동 (평소 적당한 운동이나 스포츠를 즐겨요.)</c:if>
					<c:if test="${LIST.cractive ==4}">많은 활동 (평소 강렬한 운동이나 스포츠를 즐겨요.)</c:if>
					<c:if test="${LIST.cractive ==5}">격심한 활동 (평소 매우 심한 운동을 하거나 육체를 쓰는 직업이예요.)</c:if>
				</td>
			</tr>
			<tr>
				<th style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">기초대사량</th>
				<td style="padding:25px;border-bottom:0;">${LIST.crbmr}</td>
			</tr>
			<tr>
				<th style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">다이어트를 위한 하루 칼로리</th>
				<td style="padding:25px;border-bottom:0;">${LIST.crcal} kcal
					<c:if test="${LIST.crcal <= 1000}">하루 1000kcal이하의 섭취량은 권장하지 않습니다.</c:if>
				</td>
			</tr>
		</table>
		<div class="center">
			<a class="btn" id="check" style="height:60px;line-height:45px;">칼로리 처방 다시 받기</a>
			<c:if test="${!empty sessionScope.MID && empty sessionScope.MPW}">
				<a href="javascript:;" id="kakao-link-btn">
				<img src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"/>
				</a>
			</c:if>
		</div>
	</div>

<script type="text/javascript">
    Kakao.init('52ee84c11b882c5898d68b339bf4f9d0');
    //alert(document.location.href);
    Kakao.Link.createDefaultButton({
      container: '#kakao-link-btn',
      objectType: 'feed',
      content: {
        title: document.title,
        description: '칼로리 처방',
        imageUrl: "${pageContext.request.contextPath}/resources/img/allight_logo.jpg",
        link: {
          webUrl: document.location.href
        }
      },
      social: {
        likeCount: 286,
        commentCount: 45,
        sharedCount: 845
      },
      buttons: [
        {
          title: 'Open!',
          link: {
            mobileWebUrl: document.location.href,
            webUrl: document.location.href
          }
        }  
      ]
    });
  //]]>
</script>
</body>
</html>