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
		action="<%=request.getContextPath()%>/corporation/modify/admin.com?search=${param.search}&nowPage=${param.nowPage}&cono=${param.cono}"
		method="POST">
		<table>
			<tr>
				<td>기업명 :</td>
				<td>${CORPINFO.coname}<input type="hidden" id="coname"
					name="coname" value="${CORPINFO.coname}" /></td>
			</tr>
			<tr>
				<td>가입일 :</td>
				<td>${CORPINFO.cojoindate}</td>
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
					value="수정"> <a
					href="<%=request.getContextPath()%>/corporation/admin.com?search=${param.search}&nowPage=${param.nowPage}&"><input
						type="button" value="취소"></a></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>