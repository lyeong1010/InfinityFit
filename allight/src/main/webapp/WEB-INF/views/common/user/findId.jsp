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
	<script src="${pageContext.request.contextPath}/resources/js/findId.js"></script>
	<script type="text/javascript">
		function goBack(){
			window.history.back();
		}
	</script>
</head>
<body>
	<h2 class="center">아이디 찾기</h2>
	<form action="./findId.com" method="post">
		<table>
			<tr>
				<td class="center">회원가입한 이름을 입력하세요</td>
			</tr>
			<tr>
				<td class="center"><input type="text" id="mname" name="mname" placeholder="이름을 입력하세요."></td>
			</tr>
			<tr>
				<td class="center">회원정보에 등록된 이메일을 입력하세요.</td>
			</tr>
			<tr>
				<td class="center"><input type="text" id="memail" name="memail"></td>
			</tr>
			
			<tr>
				<td class="center" colspan="2">
					<button id="pre" type="button" value="취소" onclick="goBack();">이전</button>&nbsp;
					<input id="next" type="submit" value="다음" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>