<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
</head>
<body>
<div id="wrap">
<form action="./log.com" method="post">
<input type="hidden" name="reUrl" value="${reUrl}">
<input type="hidden" name="mcnt" value="${sessionScope.mcnt}"/>
<c:forEach items="${memdto.arr}" var="a" begin="1" step="2">
	<input type="hidden" name="arr" value="${a}"/>
</c:forEach>
	<div class="log-Table">
	<div class="title3" align="center">일반 로그인</div>
	<div class="center">
		<c:if test="${!empty sessionScope.mcnt}"><a style="color: red;">아이디 비밀번호를 확인해주세요. (${sessionScope.mcnt}/3)</a></c:if>
		<input type="text" id="mid" name="mid" placeholder="아이디" required="required"/>
	</div>
	<div class="center">
		
	</div>
	<div class="center">
		<input type="password" id="mpw" name="mpw" placeholder="비밀번호" required="required"/>
	</div>
	<c:if test="${sessionScope.mcnt > 3 and !empty memdto.arr}">
		<div class="center">
			<c:forEach items="${memdto.arr}" var="a" begin="0" step="2">
				<img width="20px" height="50px" src="${pageContext.request.contextPath}/resources/img/${a}.PNG">
			</c:forEach>
		</div>
		<div class="center">
			<input type="text" id="auto" name="auto" placeholder="자동입력 방지문자" required="required"/>
		</div>
	</c:if>
	<div class="center">
		<input type="submit" value="로그인" class="btn">
	</div>
	<div class="center">
		<a href="${pageContext.request.contextPath}/findIdFrm.com">아이디 찾기 |</a>&nbsp;
		<a href="${pageContext.request.contextPath}/findPwFrm.com">비밀번호 찾기 |</a>&nbsp;
		<a href="${pageContext.request.contextPath}/joinFrm.com">회원가입</a>
	</div>
	<div class="center">
		<a id="custom-login-btn" href="javascript:loginWithKakao()">
		  <img
		    src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
		    width="222"
		  />
		</a>
	</div>
	</div>
	<%-- <table>
		<tr>
			<td class="title3" style="text-align:center">일반 로그인</td>
		</tr>
		<tr>
			<td><input type="text" id="mid" name="mid" placeholder="아이디" required="required"/></td>
		</tr>
		<tr>
			<td><input type="password" id="mpw" name="mpw" placeholder="비밀번호" required="required"/></td>
		</tr>
		<c:if test="${sessionScope.mcnt > 3 and !empty memdto.arr}">
			<tr>
				<td><c:forEach items="${memdto.arr}" var="a" begin="0" step="2">
					<img width="20px" height="50px" src="${pageContext.request.contextPath}/resources/img/${a}.PNG">
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td><input type="text" id="auto" name="auto" placeholder="자동입력 방지문자" required="required"/></td>
			</tr>
		</c:if>
		<tr>
			<td><input type="submit" value="로그인"></td>
		</tr>
		<tr class="center">
			<td colspan="2">
				<a href="${pageContext.request.contextPath}/findIdFrm.com">아이디 찾기 |</a>&nbsp;
				<a href="${pageContext.request.contextPath}/findPwFrm.com">비밀번호 찾기 |</a>&nbsp;
				<a href="${pageContext.request.contextPath}/joinFrm.com">회원가입</a>
			</td>
		</tr>
	</table> --%>
	
</form>

<!-- <a id="custom-login-btn" href="javascript:loginWithKakao()">
  <img
    src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
    width="222"
  />
</a> -->
<script type="text/javascript">
  // input your appkey
  Kakao.init('52ee84c11b882c5898d68b339bf4f9d0')
 function loginWithKakao() {
    Kakao.Auth.login({
      success: function(authObj) {
   	 Kakao.API.request({
	        url: '/v2/user/me',
	        success: function(res) {
	          console.log(res);
 			  $.ajax({
				  url:'./kakao.com',
				  type:'post',
				  dataType:'text',//받을때
				  data:res,
				  success:function(check){
					  if(check=="check"){
					  	location.href = "${pageContext.request.contextPath}/kakaojoin.com";
					  }else{
						  location.href = "${pageContext.request.contextPath}/main.com";
					  }
				  },
				  fail:function(error){
					  console.log("error")
				  }
			  });
	        },
	        fail: function(error) {
	        	console.log("errorrrrr")
	        },
	      })
      },
      fail: function(err) {
    	  console.log(err)
      },
    })
  }
</script>

<form action="./corlog.com" method="post">
<input type="hidden" name="ccnt" value="${sessionScope.ccnt}"/>
<c:forEach items="${cordto.arr}" var="a" begin="1" step="2">
	<input type="hidden" name="arr" value="${a}"/>
</c:forEach>
<div class="log-Table">
	<div class="title3" align="center">기업 로그인</div>
	<div class="center">
		<c:if test="${!empty sessionScope.ccnt}"><a style="color: red;">아이디 비밀번호를 확인해주세요. (${sessionScope.ccnt}/3)</a></c:if>
		<input type="text" id="coid" name="coid" placeholder="아이디" required="required"/>
	</div>
	<div class="center">
		
	</div>
	<div class="center">
		<input type="password" id="copw" name="copw" placeholder="비밀번호" required="required"/>
	</div>
	<c:if test="${sessionScope.ccnt > 3 and !empty cordto.arr}">
		<div class="center" style="width:50%">
			<c:forEach items="${cordto.arr}" var="a" begin="0" step="2">
				<img width="30px" height="50px" src="${pageContext.request.contextPath}/resources/img/${a}.PNG">
			</c:forEach>
		</div>
		<div class="center">
			<input type="text" id="auto" name="auto" placeholder="자동입력 방지문자" required="required"/>
		</div>
	</c:if>
	<div class="center">
		<input type="submit" value="로그인" class="btn">
	</div>
	</div>
<%-- <c:if test="${!empty sessionScope.ccnt}">${sessionScope.ccnt}<a style="color: red;">아이디 비밀번호를 확인해주세요.</a></c:if>
	<div class="log-Table">
	안녕하세요
	</div>
	<table>
		<tr>
			<td class="title3" style="text-align:center">기업 로그인</td>
		</tr>
		<tr>
			<td><input type="text" id="coid" name="coid" placeholder="아이디" required="required"/></td>
		</tr>
		<tr>
			<td><input type="password" id="copw" name="copw" placeholder="비밀번호" required="required"/></td>
		</tr>
		<c:if test="${sessionScope.ccnt > 3 and !empty cordto.arr}">
			<tr>
				<td><c:forEach items="${cordto.arr}" var="a" begin="0" step="2">
					<img width="20px" height="50px" src="${pageContext.request.contextPath}/resources/img/${a}.PNG">
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td><input type="text" id="auto" name="auto" placeholder="자동입력 방지문자" required="required"/></td>
			</tr>
		</c:if>
		<tr>
			<td><input type="submit" value="로그인"></td>
		</tr>
	</table> --%>
	
</form>
</div>
</body>
</html>