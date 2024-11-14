<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title></title>
	<!-- jQuery CDN -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	<script>
	$(function(){
		
	});
	</script>
</head>
<body>
	${sessionScope.MID }님, 결제가 완료되었습니다.

<table>	
	<tr>
		<td colspan="2" class="center">
			<input type="button" value="홈" onclick="location.href='${pageContext.request.contextPath}/main.com'" />&nbsp;
			<input type="button" value="주문/배송조회" onclick="location.href='${pageContext.request.contextPath}/order/mypage/list.com'"/>
		</td>
	</tr>
</table>
</body>
</html>