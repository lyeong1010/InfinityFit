<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = (String)session.getAttribute("MID");
    if(id==null){
    	response.sendRedirect("loginForm.jsp");
    }else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 폼</title>
<script type="text/javascript">
	window.onload=function(){
		document.myForm.id.focus();
	};
	function checkIt(){
		var user = document.myForm;
		if(user.id.value==''){
			alert('아이디를 입력하세요!');
			user.id.focus();
			return false;
		}
		if(user.passwd.value==''){
			alert('비밀번호를 입력하세요!');
			user.passwd.focus();
			return false;
		}
	}
</script>

</head>
<body>
<form name="myForm" action="./delete.com" method="post" onsubmit="return checkIt()">
	<div class="center">
		<div class="title3">회원탈퇴</div>
		<table style="padding">
			<tr>
				<th width="30%" style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">아이디</th>
				<td style="padding:25px;border-bottom:0;">${MEMINFO.mid}</td>
			</tr>
			<tr>
				<th style="padding:25px;border-bottom:0;border-right:2px solid #bdbdbd;">비밀번호</th>
				<td style="padding:25px;border-bottom:0;"><input type="password" name="mpw" size="15" maxlength="12"></td>
			</tr>
		</table>
	</div>
	<div class="center" style="margin:50px">
		<input type="submit" value="회원 탈퇴" class="btn5" style="height:36px;line-hight:20px">
		<input type="reset" value="초기화" class="btn">
		<input type="button" value="홈으로" class="btn" onclick="location.href='<%=request.getContextPath()%>/main.com'">
		<input type="hidden" name="mid" value="${MEMINFO.mid}">
	</div>
</form>

</body>
</html>
<%
    }
    %>