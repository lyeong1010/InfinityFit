<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form
		action="<%=request.getContextPath()%>/corporation/modify/corp.com"
		method="POST">
		<table>
		<input type="hidden" id="cono" name="cono" value="${CORPINFO.cono}" />
			<tr>
				<td>기업명 :</td>
				<td>${CORPINFO.coname}<input type="hidden" id="coname"
					name="coname" value="${CORPINFO.coname}" /></td>
			</tr>
			<tr>
				<td>전화번호 :</td>
				<td><input type="text" id="cotel" name="cotel"
					value="${CORPINFO.cotel}" /></td>
			</tr>
			<tr>
				<td>이메일 :</td>
				<td><input type="text" id="coemail" name="coemail"
					value="${CORPINFO.coemail}" /></td>
			</tr>
			<tr>
				<td>아이디 :</td>
				<td>${CORPINFO.coid}<input type="hidden" id="coid" name="coid"
					value="${CORPINFO.coid}" /></td>
			</tr>
			<tr>
				<td>비밀번호 :</td>
				<td><input type="password" id="copw" name="copw"
					value="${CORPINFO.copw}" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center"><input type="submit"
					value="수정" class="btn5">
					<input type="button" class="btn" value="취소" onclick="location.href='<%=request.getContextPath()%>/main.com'"></td>
			</tr>
		</table>
	</form>
</body>
</html>