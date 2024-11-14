<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head title="회원가입">
<script
	src="${pageContext.request.contextPath}/resources/memberjs/join.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
	//모든 공백 체크 정규식
	var empJ = /\s/g;
	//아이디 정규식
	var idJ = /^[a-z0-9]{4,12}$/;
	// 비밀번호 정규식
	var pwJ = /^[A-Za-z0-9]{4,12}$/;
	// 이름 정규식
	var nameJ = /^[가-힣]{2,6}$/;
	// 휴대폰 번호 정규식
	var phoneJ = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;

	$(function() {
		$("#idCheck").click(function() {
			var coid = $("#coid").val();
			//alert(mid);
			// JSON.parse(제이슨데이터); 수신
			// JSON.stringfy(자바스크립트오브젝트); 송신
			$.ajax({
				url : "${pageContext.request.contextPath}/corpIdChk.com",
				type : "POST",
				//dataType: "json"인 경우 해당 컨트롤러(idChk)에 @ResponceBody 어노테이션을 추가해야한다
				dataType : "text",
				async : false,
				data : {
					coid : $("#coid").val()
				},
				success : function(data) {
					if (data == "fail") {
						alert("이미 등록된 아이디입니다.");
						$("#coid").val("");
						$("#coid").focus();
					} else {
						alert("사용 가능한 아이디입니다.");
						$('#idChecked').val("1");	
					}
				}
			});
		});
		$("#join").submit(function(){
		if($("#coid").val().length==0){
			alert("아이디를 입력해주세요")
			$("#coid").focus();
			return false;
		}
		if($("#idChecked").val()=="0"){
			alert("아이디 중복확인을 해주세요.")
			return false;
		}
		if($("#coname").val().length==0){
			alert("기업이름을 입력해주세요")
			$("#coname").focus();
			return false;
		}
		if($("#copw").val().length==0){
			alert("비밀번호를 입력해주세요")
			$("#copw").focus();
			return false;
		}
		if($("#copw2").val().length==0){
			alert("비밀번호를 입력해주세요")
			$("#copw2").focus();
			return false;
		}
		if($("#coemail").val().length==0){
			alert("이메일을 입력해주세요")
			$("#coemail").focus();
			return false;
		}
		if($("#cotel").val().length==0){
			alert("전화번호를 입력해주세요")
			$("#cotel").focus();
			return false;
		}
		});
	});

</script>
</head>
<body>
	<iframe id="joinFrm"
		style="position: absolute; z-index: 1; visibility: hidden;"></iframe>
	<div class="title3">기업 회원가입</div>
	<hr />
	<form
		action="<%=request.getContextPath()%>/corporation/join/admin.com"
		id="join" method="post">
		<input type="hidden" id="reqMethod" name=reqMethod value="">
		<table>
			<tr>
				<td>아이디</td>
				<td><input type="text" id="coid" name="coid">
					<button id="idCheck" type="button">중복확인</button>
					<input id="idChecked" type="hidden" value="0"/></td>
			</tr>
			<tr>
				<td>기업이름</td>
				<td><input type="text" id="coname" name="coname"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" id="copw" name="copw"></td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td><input type="password" id="copw2" name="copw2""></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" id="coemail" name="coemail"
					style="width: 80">&nbsp;@ <select id="memail2"
					name="memail2">
						<option value="">선택하세요</option>
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="hanmail.net">daum.net</option>
						<option value="nate.com">nate.com</option>
						<option value="yahoo.com">yahoo.com</option>
				</select></td>
			</tr>
			<tr>
				<td>핸드폰번호</td>
				<td><input id="cotel" type="text" name="cotel"></td>
			</tr>
			<tr>
				<td colspan="2" class="center"><a href="#"
					onclick="frmChk('this.form');return false;"> <input
						type="submit" value="등록" class="btn5"/></a> <input type="reset" value="취소" class="btn"
					onclick="location.href='${pageContext.request.contextPath}/admin.com'" />&nbsp;
				</td>
			</tr>
		</table>
	</form>
</body>
</html>