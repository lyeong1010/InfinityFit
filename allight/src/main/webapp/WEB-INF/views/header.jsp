<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<title>ALLIGHT - 다이어트 관리 홈페이지</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/animate.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/owl.carousel.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/owl.theme.default.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/magnific-popup.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/shopping.css">
<link rel="stylesheet" 
  	href="${pageContext.request.contextPath}/resources/css/calendar.css">
  	
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript">

function kakaoLogout() {
	Kakao.init('52ee84c11b882c5898d68b339bf4f9d0')
	if (!Kakao.Auth.getAccessToken()) {
		alert('Not logged in.')
		}
	Kakao.Auth.logout(function() {
		$.ajax({
			url:'./kakaout.com',
			dataType:'text',//받을때
			type:'post',
			success:function(check){
				if(check=="out"){
					location.href = "${pageContext.request.contextPath}/main.com";
				}else{
					alert("카카오 로그아웃 실패")
					location.href = "${pageContext.request.contextPath}/main.com";
				}
				},
				fail:function(error){
					console.log("error")
					}
				});
		})
	}
</script>
</head>

<body>
	<!-------------------- header -------------------->
	<!-- login/join -->
	<div class="header-top">
		<div class="container">
			<div class="ht-right">
				<!-- 로그인X -->
				<c:if test="${empty sessionScope.COID and empty sessionScope.MNICK}">
					<a href="${pageContext.request.contextPath}/login.com"
						class="login-panel"><i class="fa fa-user"></i>Login</a>
					<a href="${pageContext.request.contextPath}/joinFrm.com"
						class="join-panel">join</a>
				</c:if>
				
				<!-- 회원 -->
				<c:if test="${!empty sessionScope.MID and !empty sessionScope.MPW}">
					<a href="${pageContext.request.contextPath}/logout.com"
						class="logined-panel">로그아웃</a>
					<a href="${pageContext.request.contextPath}/mypage/cart.com" class="logined-panel"><i class="fa fa-shopping-cart" aria-hidden="true"></i>&nbsp;장바구니</a>
					
					<a href="${pageContext.request.contextPath}/order/mypage/list.com"
						class="logined-panel">주문/배송조회</a>
					<a href="${pageContext.request.contextPath}/mypage/home.com" class="logined-panel">마이페이지</a>
					<a href="#" class="logined-nick-panel">${sessionScope.MID} 님</a>
				</c:if>
				
				<!-- 카카오 -->
				<c:if test="${empty sessionScope.MPW and !empty sessionScope.MID and !empty sessionScope.MNICK}">
					<a class="logined-panel" onclick="kakaoLogout()">로그아웃</a>
					<a href="#" class="logined-panel">장바구니()</a>
					<a href="${pageContext.request.contextPath}/order/mypage/list.com"
						class="logined-panel">주문/배송조회</a>
					<a href="${pageContext.request.contextPath}/mypage/home.com" class="logined-panel">마이페이지</a>
					<a href="#" class="logined-nick-panel">${sessionScope.MNICK} 님</a>
				</c:if>
				<c:if test="${!empty sessionScope.COID}">
					<a href="${pageContext.request.contextPath}/corlogout.com"
						class="logined-panel">로그아웃</a>
					<a href="#" class="logined-nick-panel">${sessionScope.COID} 님</a>
				</c:if>
			</div>
		</div>
	</div>

	<!-- logo -->
	<div class="container">
		<a href="${pageContext.request.contextPath}/main.com"> <img
			src="${pageContext.request.contextPath}/resources/img/allight_logo2.PNG"
			class="logo" onclick="" />
		</a>
	</div>

	<!-- navigation bar -->
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar">
		<div class="container-fluid">
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav m-auto">
					<li class="nav-item active">
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/main.com"
						class="nav-link">다이어리</a></li>
					<li class="nav-item">
						<a href="${pageContext.request.contextPath}/cal/dictionary/food.com" class="nav-link">칼로리 사전</a>
						<ul class="dropdown">
							<li>
								<a href="${pageContext.request.contextPath}/cal/dictionary/food.com">음식 사전</a></li>
							<li>
								<a href="${pageContext.request.contextPath}/cal/dictionary/exercise.com">운동 사전</a></li>
						</ul>
					</li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/calorie_recipe.com" class="nav-link">칼로리 처방</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/freeboard/list.com" class="nav-link">커뮤니티</a>
						<ul class="dropdown">
							<li><a href="${pageContext.request.contextPath}/freeboard/list.com">자유게시판</a></li>
							<li><a href="${pageContext.request.contextPath}/notice.com">공지사항</a></li>
							<li><a href="${pageContext.request.contextPath}/question/list.com">문의사항</a></li>
						</ul></li>
					<li class="nav-item">
						<a href="${pageContext.request.contextPath}/shopping/list.com" class="nav-link">쇼핑</a>
						<ul class="dropdown">
							<li>
								<a href="${pageContext.request.contextPath}/shopping/list.com?icategory=식단">식단</a></li>
							<li>
								<a href="${pageContext.request.contextPath}/shopping/list.com?icategory=간식">간식</a></li>
							<li>
								<a href="${pageContext.request.contextPath}/shopping/list.com?icategory=운동복">운동복</a></li>
							<li>
								<a href="${pageContext.request.contextPath}/shopping/list.com?icategory=기타">기타</a></li>
							<li>
								<a href="${pageContext.request.contextPath}/shopping/brand.com">브랜드관</a></li>
						</ul>
					</li>
					<!-- 회원 로그인시  -->
					<c:if test="${sessionScope.MTYPE eq 0 && (!empty sessionScope.MID and !empty sessionScope.MPW) || (empty sessionScope.MPW and !empty sessionScope.MID and !empty sessionScope.MNICK)}">
					<li class="nav-item"><a href="${pageContext.request.contextPath}/order/mypage/list.com" class="nav-link">마이페이지</a>
						<ul class="dropdown">
							<li><a
								href="${pageContext.request.contextPath}/order/mypage/list.com">주문/배송조회</a></li>
							<li><a
								href="${pageContext.request.contextPath}/order/mypage/back.com">취소/반품조회</a></li>
							<li><a href="${pageContext.request.contextPath}/mypage/cart.com">&nbsp;장바구니</a></li>
							<li><a
								href="${pageContext.request.contextPath}/member/mypage/address.com">배송지
									관리</a></li>
							<li><a href="${pageContext.request.contextPath}/item/review/mypage/list.com">상품 문의</a></li>
							<li><a href="${pageContext.request.contextPath}/mypage/member/modify.com">내 정보</a></li>
						</ul>
					</li>
					</c:if>

					<!-- 기업 로그인시   -->
					<c:if test="${!empty sessionScope.COID}">
					<li class="nav-item"><a href="${pageContext.request.contextPath}/order/list/corp.com" class="nav-link">기업</a>
						<ul class="dropdown">
							<li><a
								href="${pageContext.request.contextPath}/order/list/corp.com">주문관리</a></li>
							<li><a
								href="${pageContext.request.contextPath}/item/list/corp.com">상품관리</a></li>
							<li><a href="${pageContext.request.contextPath}/item/review/list/corp.com">상품문의</a></li>
							<li><a
								href="${pageContext.request.contextPath}/question/list/corp.com">문의사항</a></li>
							<li><a href="${pageContext.request.contextPath}/corporation/modify/corp.com?cono=${sessionScope.CONO}">기업 정보 관리</a></li>
						</ul>
					</li>
					</c:if>

					<!-- 관리자 로그인시   -->
					<c:if test="${sessionScope.MTYPE eq 1 && !empty sessionScope.MID}">
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/member/admin.com"
						class="nav-link">관리자</a>
						<ul class="dropdown">
							<li><a href="${pageContext.request.contextPath}/admin.com">기업/회원
									관리</a></li>
							<li><a
								href="${pageContext.request.contextPath}/item/list/admin.com">기업
									상품 관리</a></li>
							<li><a
								href="${pageContext.request.contextPath}/question/list/admin.com">기업
									문의 관리</a></li>
							<li><a href="#">커뮤니티 관리</a></li>
							<li><a href="${pageContext.request.contextPath}/cal/dictionary/admin.com">칼로리 사전 관리</a></li>
						</ul>
					</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	<hr />
</body>
</html>