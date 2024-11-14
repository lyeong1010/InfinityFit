<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
$(function() {
	$("#mnick").change(function(){
		//alert("닉네임 수정됨");
		$('#nickChecked').val("0");
	})
	//닉네임중복확인.
	$("#nickCheck").click(function() {
		var mnick = $("#mnick").val();
		$.ajax({
			url : "../../nickChk.com",
			type : "POST",
			dataType : "text",
			async : false,
			data :{ 
				mnick : $("#mnick").val()
			},
			success : function(data) {
				if (data == "fail") {
					alert("이미 등록된 닉네임입니다.");
					$("#mnick").focus();
				} else {
					alert("사용 가능한 닉네임입니다.");
					$('#nickChecked').val("1");
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status+ "\n" + "message:"+ request.responseText+ "\n" + "error:" + error);
			}
		});
	});
	$("#modify").submit(function(){
		//닉네임 중복확인여부 체크
		//alert("중복체크들어간다");
		//수정되었지만 수정한 닉네임이 기존과 같다면 바로넘김 
		if($("#mnick").val()=="${MEMINFO.mnick}"){
			$('#nickChecked').val("1");
		}
		if($("#nickChecked").val()=="0"){
			alert("닉네임 중복확인을 해주세요.")
			return false;
		}
		//닉네임 입력 여부
		if($("#mnick").val().length==0){
			alert("닉네임을 입력하지 않았습니다.")
			$("#mnick").focus();
			return false;
		}
		//닉네임 길이 체크
		if($("#mnick").val().length<3 || $("#mnick").val().length>8){
			alert("닉네임은 3~8자리로 입력해주세요.")
			$("#mnick").focus();
			return false;
		}
		//비밀번호 입력여부
		if($("#mpw").val().length==0){
			alert("비밀번호를 입력하지 않았습니다.")
			$("#mpw").focus();
			return false;
		}
		if($("#mpw2").val().length==0){
			alert("비밀번호를 입력하지 않았습니다.")
			$("#mpw2").focus();
			return false;
		}
		//비밀번호 길이 체크
		if($("#mpw").val().length<8 || $("#mpw").val().length>16){
			alert("비밀번호는 8~16자리로 입력해주세요.")
			$("#mpw").focus();
			return false;
		}
		//비밀번호 일치 여부
		if($("#mpw").val()!=$("#mpw2").val()){
		    alert('비밀번호가 일치 하지 않습니다')
		    frm.mpw2.value='';
		    frm.mpw2.focus();
		    return false;
		    }
		//전화번호 입력여부 
		if($("#mtel").val().selectedIndex<1){
			alert("전화번호를 입력하지 않았습니다.")
			$("#mtel").focus();
			return false;
		}
		if($("#mtel1").val().length==0){
			alert("전화번호를 입력하지 않았습니다.")
			$("#mtel1").focus();
			return false;
		}
		if($("#mtel2").val().length==0){
			alert("전화번호를 입력하지 않았습니다.")
			$("#mtel2").focus();
			return false;
		}
	})
});

</script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<form
		action="<%=request.getContextPath()%>/member/modify/admin.com?mno=${param.mno}"
		method="post" name="modify" id="modify">
		<table>
			<tr>
				<td>아이디 :</td>
				<td>${MEMINFO.mid}<input type="hidden" id="mid" name="mid"
					value="${MEMINFO.mid}" /></td>
			</tr>
			<tr>
				<td>이름 :</td>
				<td>${MEMINFO.mname}<input type="hidden" id="mname" name="mname"
					value="${MEMINFO.mname}" /></td>
			</tr>
			<tr>
				<td>가입일 :</td>
				<td>${MEMINFO.mjoindate}<input type="hidden" id="mjoindate" name="mjoindate"
					value="${MEMINFO.mjoindate}" /></td>
			</tr>
			<tr>
				<td>최근접속일 :</td>
				<td>${MEMINFO.mlogdate}</td>
			</tr>
			<tr>
				<td>이메일 :</td>
				<td><input type="hidden" id="memail" name="memail"
					value="${MEMINFO.memail}" /></td>
			</tr>
			<tr> 
				<td>성별 :</td>
				<td>${MEMINFO.msex}<input type="hidden" id="msex" name="msex"
					value="${MEMINFO.msex}" /></td>
			</tr>
			<tr>
				<td>생년월일 :</td>
				<td>${MEMINFO.mbirth}<input type="hidden" id="mbirth"
					name="mbirth" value="${MEMINFO.mbirth}" /></td>
			</tr>
			<tr>
				<td>닉네임 :</td>
				<td><input type="text" id="mnick"
					name="mnick" value="${MEMINFO.mnick}" />
					<button id="nickCheck" type="button">중복확인</button>
					<input type="hidden" id="nickChecked" value="1" /></td>
			</tr>
			<tr>
				<td>비밀번호 :</td>
				<td><input type="password" id="mpw" name="mpw"
					value="${MEMINFO.mpw}" /></td>
			</tr>
			<tr>
				<td>비밀번호 확인 :</td>
				<td><input type="password" id="mpw2" name="mpw2"
					value="${MEMINFO.mpw}" /></td>
			</tr>
			<tr>
				<td>핸드폰번호 :</td>
				<td><input type="text" id="mtel" name="mtel"
					value="${MEMINFO.mtel}" /></td>
			</tr>
			<tr>
				<td>구분 :</td>
				<td>
				<c:if test="${MEMINFO.mtype==1}">
				<select name="mtype" id="mtype" required="required">
						<option value="0">회원</option>
						<option value="1" selected>관리자</option>
				</select>
				</c:if>
				<c:if test="${MEMINFO.mtype==0}">
				<select name="mtype" id="mtype" required="required">
						<option value="0" selected>회원</option>
						<option value="1">관리자</option>
				</select>
				</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center"><input type="submit"
					value="수정"> <a
					href="<%=request.getContextPath()%>/member/admin.com?search=${param.search}&nowPage=${param.nowPage}"><input
						type="button" value="취소"></a></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>