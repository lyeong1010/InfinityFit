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
		$('#write').click(function(){
			$('#wfrm').submit();
		});
	})
</script>
</head>
<body>
<form id="wfrm" action="./confirm.com" method="post">
	<input type="hidden" name="odno" value="${oddto.odno}"/>
	<input type="hidden" name="ostatus" value="${oddto.ostatus}"/>
	<div class="container">
		<table class="table">
			<tr>
				<th>받는사람</th>
				<th width="70%">${MDTO.mname}</th>
			</tr>
			<tr>
				<th>은행명</th>
				<th width="70%"><input type="text" name="mbank" value="${MDTO.mbank}" /></th>
			</tr>
			<tr>
				<th>계좌번호</th>
				<th width="70%"><input type="text" name="mbankno" value="${MDTO.mbankno}" /></th>
			</tr>
		</table>
		<div class="right">
			<input type="button" id="write" value="변경"/>
		</div>
	</div>
</form>
</body>
</html>