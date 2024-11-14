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
	<script src="${pageContext.request.contextPath}/resources/js/findPw.js" ></script>
	<script type="text/javascript">
		function goBack(){
			window.history.back();
		}
	</script>
</head>
<body>
	<h2 class="center">비밀번호 찾기</h2>
	<hr />
	<form action="./findPw.com" id="findPw" method="post">
		<table>
			<tr>
				<td >아이디</td>
				<td><input type="text" id="mid" name="mid" placeholder="아이디를 입력하세요."></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" id="memail" name="memail" style="width: 80px" value="${param.memail}">&nbsp;@
					<select id="memail2" name="memail2">
						<option value="">선택하세요</option>
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="hanmail.net">daum.net</option>
						<option value="nate.com">nate.com</option>
						<option value="yahoo.com">yahoo.com</option>
					</select>
					<button type="button" class="btn btn-outline-secondary" id="check_pw_mail">이메일인증</button>
					</td>
				<td id="checkCODE" style="display:none;">
					<input type="text" id="usercode" name="usercode" style="width:200px;" placeholder="인증번호를 입력하세요"/>
					<button type="button" class="btn btn-secondary" id="check_pw_code">인증하기</button>
				</td>
			</tr>
			<tr>
				<td>${fail}</td>
			</tr>
			<tr>
				<td colspan="2">
					<button id="pre" type="button" value="취소" onclick="goBack();">이전</button>&nbsp;
					<input id="next" type="submit" value="다음" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>