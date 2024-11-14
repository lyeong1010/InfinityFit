<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		// 해당하는 페이지일때 왼쪽 메뉴 글씨 볼드체
		var href = location.href.substr(location.href.indexOf("allight")-1)
		$('.left_menu_box li a').each(function(){
			if(href.indexOf($(this).attr('href'))!=-1){
				$(this).parent('li').attr('class','on')
			}
		}) 
	})
</script>
<decorator:head />
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div id="wrap">
	<div class="wrap-left">
		<div class="left_menu">
			<p class="left_tit">
				<a href="#">기업</a>
			</p>
			<ul class="left_menu_box">
				<li class="left_menu_m1">주문관리</li>
				<li><a href="${pageContext.request.contextPath}/order/list/corp.com">주문/배송관리</a></li>
			</ul>

			<ul class="left_menu_box">
				<li class="left_menu_m1">상품관리</li>
				<li><a href="${pageContext.request.contextPath}/item/list/corp.com">상품 등록/수정/삭제</a></li>
			</ul>

			<ul class="left_menu_box">
				<li class="left_menu_m1">문의/리뷰관리</li>
				<li><a href="${pageContext.request.contextPath}/item/review/list/corp.com">상품 문의 관리</a></li>
				<li><a href="${pageContext.request.contextPath}/review/list/corp.com">상품 리뷰 관리</a></li>
			</ul>

			<ul class="left_menu_box">
				<li class="left_menu_m1">관리자에게</li>
				<li><a href="${pageContext.request.contextPath}/question/list/corp.com">문의사항</a></li>
			</ul>

			<ul class="left_menu_box last">
				<li class="left_menu_m1">기업정보관리</li>
				<li><a href="${pageContext.request.contextPath}/corporation/modify/corp.com?cono=${sessionScope.CONO}">기업정보 확인/수정</a></li>
			</ul>
		</div>
	</div>

	<div class="wrap-right">
		<decorator:body />
	</div>

</div>

<jsp:include page="/WEB-INF/views/footer.jsp"/>

</body>
</html>