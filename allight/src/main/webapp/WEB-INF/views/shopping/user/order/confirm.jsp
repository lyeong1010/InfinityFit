<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#val").click(function() {
			var odno =  "${oddto.odno}";
			var ostatus = "${oddto.ostatus}";
			var param = { "odno" : odno , "ostatus" : ostatus };
			$.ajax({
				url : "${pageContext.request.contextPath}/order/change.com",
				type : 'post',
				data : param,
				success : function(data) {
						if (confirm("해당 상품을 주문 내역이 변경되었습니다.")) {
						location.href = "${pageContext.request.contextPath}/order/mypage/list.com";
						}
					},
					error : function(request,status,error) {
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
				});
		});
	});
</script>
</head>
<body>
	<div class="container">
		<table class="table">
			<tr>
				<th>받는사람</th>
				<th width="70%">${MDTO.mname}</th>
			</tr>
			<tr>
				<th>은행명</th>
				<th width="70%">${MDTO.mbank}</th>
			</tr>
			<tr>
				<th>계좌번호</th>
				<th width="70%">${MDTO.mbankno}</th>
			</tr>
		</table>
		<div class="right">
			<a class="btn" href="${pageContext.request.contextPath}/order/mypage/check.com?odno=${oddto.odno}&ostatus=${oddto.ostatus}">취소</a>
			<a class="btn" id="val">확인</a>
		</div>
	</div>
</body>
</html>