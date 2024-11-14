<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(
			function() {
				//목록 버튼 클릭 시
				$("#list").click(function() {
					$(location).attr("href","${pageContext.request.contextPath}/order/list/corp.com?nowPage=${NOW}");
				 });
				//변경
				$("#serb").click(function() {
					/* alert($("#status").text());
					alert($("#com").val());
					alert($("#num").val()); */
					if($("#status option:selected").val()=='배송시작'){
						if($("#com").val()=="" ||$("#num").val()==""){
							alert("택배회사와 송장번호를 입력하세요.")
						}
					}
					$("#statfrm").submit();
				});
				//택배
				$("#delib").click(function() {
					if($("#com").val()==""){
						alert("택배회사를 입력하세요.")
						$("#com").focus();
						return false;
					}
					if($("#num").val()==""){
						alert("송장번호를 입력하세요.")
						$("#num").focus();
						return false;
					}
					if($("#ost").text()!="배송시작"){
						alert("상태를 배송시작으로 변경하세요.")
						$("#status").focus();
						return false;
					}
					$("#delifrm").submit();
				});
				
				$("#mail").click(function() {
					//택배값,주문취소 email
					$.ajax({
						url : "${pageContext.request.contextPath}/order/email/corp.com?no="+${param.no}+"&email=${ORDER.mdto1.memail}&text="+$("#ost").text(),
						type : "POST",
						success : function(data) {   
							if (data == "delemail") {
								alert("배송 시작 이메일 발송이 완료되었습니다.")
							}else if(data == "canemail"){
								alert("주문 취소 이메일 발송이 완료되었습니다.")
							}else if(data == "reemail"){
								alert("반품 완료 이메일 발송이 완료되었습니다.")
							}else if(data == "x"){
								alert("이메일 발송을 할 수 없는 상태입니다.")
							}else{
								alert("이메일 발송을 실패했습니다.")
							} 
						}, error : function() {
							console.log("실패");
						}
					});
				});
			})
</script>
</head>
<body>
	<div>
		<div>
			<div>
				<input type="button" value="목록" class="btn" id="list" style="float: right;margin: 0 10px;">
			</div>
			<div>
				<p >주문번호   ${ORDER.odto1.ordernum}<em> l </em>주문일  ${ORDER.odto1.odate}
				</p>
			</div>
		</div>

		<table>
			<colgroup>
				<col width="120">
				<col width="auto">
				<col width="60">
				<col width="100">
				<col width="150">
			</colgroup>
			<thead>
				<tr>
					<th scope="col" colspan="2">상품정보</th>
					<th scope="col">수량</th>
					<th scope="col">주문 금액</th>
					<th scope="col">진행 상태</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${ORDER.sdto1.ino}"> <img alt="temp_thmb"
							src="${ORDER.sdto1.imgimage}" class="product-image"></a></td>
					<td><a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${ORDER.sdto1.ino}">${ORDER.sdto1.iname}</a>
						<ul>
													<li>${ORDER.sdto1.iprice}</li>
												</ul>
												<td><p style="margin: 40px 0">X ${ORDER.oddto1.odamount}</p></td>
												<td><p style="margin: 40px 0">= ${ORDER.oddto1.odprice}원</p></td>
					<td>
						<ul>
							<li style="font-weight:bold;margin: 0 0 10px 5px;" id="ost">${ORDER.oddto1.ostatus}</li>
							<li><form id="statfrm" action="${pageContext.request.contextPath}/order/change/corp.com">
									<input type="hidden" name="odno" value="${ORDER.oddto1.odno}">
									<input type="hidden" name="ino" value="${ORDER.oddto1.ino}">
									<input type="hidden" name="odamount" value="${ORDER.oddto1.odamount}">
									<select name="ostatus" class="selectCss" id="status">
										<option selected="selected">상태변경</option>
										<option value="배송준비중">배송준비중</option>
										<option value="배송시작">배송시작</option>
										<option value="배송완료">배송완료</option>
										<option value="주문취소">주문취소</option>
										<option value="반품">반품완료</option>
									</select> <input type="button" id="serb" value="변경" />
								</form></li>
						</ul>
					</td>
				</tr>
			</tbody>
		</table>

		<!-- 주문자정보 -->
		<div>
			 
			<h3 >주문자 정보</h3>
			<table>
				 
				<colgroup>
					<col width="140">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>보내는사람</th>
						<td>${ORDER.mdto1.mname}</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>${ORDER.mdto1.mtel}</td>
					</tr>
					<tr>
						<th>Email</th>
						<td>${ORDER.mdto1.memail}</td>
					</tr>
					<tr>
						<th>은행</th>
						<td>${ORDER.mdto1.mbank}</td>
					</tr>
					<tr>
						<th>계좌번호</th>
						<td>${ORDER.mdto1.mbankno}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- 배송지정보 -->
		<div>
			 
			<h3>배송지 정보</h3>
			<table>
				 
				<colgroup>
					<col width="140">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>받으실분</th>
						<td>${ORDER.odto1.oreceiver}</td>
					</tr>

					<tr>
						<th>배송지 주소</th>
						<td>(${ORDER.odto1.oaddno})${ORDER.odto1.oaddress1}&nbsp;${ORDER.odto1.oaddress2}</td>
					</tr>

					<tr>
						<th><span>연락처</span></th>
						<td>${ORDER.odto1.otel}</td>
					</tr>
					<tr>
						<th><span>배송요청사항</span></th>
						<td>${ORDER.odto1.orequirethings}</td>
					</tr>
					<form id="delifrm" action="${pageContext.request.contextPath}/order/delivery/corp.com">
						<tr>
							<input type="hidden" name="odno" value="${ORDER.oddto1.odno}">
							<th><span>택배회사</span></th>
							<td><input type="text" name="ocouriercompany" id="com" value="${ORDER.oddto1.ocouriercompany}"></td>
						</tr>
						<tr>
							<th><span>송장번호</span></th>
							<td><input type="text" name="oinvoicenumber" id="num" value="${ORDER.oddto1.oinvoicenumber}"> <input
								type="button" id="delib" value="확인"><input	type="button" id="mail" value="이메일발송"></td>
						</tr>
					</form>
				</tbody>
			</table>
		</div>

		<!-- 결제정보 -->
		<div>
			 
			<h3>결제 정보</h3>
			<table>
				 
				<colgroup>
					<col width="140">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>결제 수단</th>
						<td>${ORDER.odto1.opayment}</span></td>
					</tr>
					<tr>
						<th>승인일시</th>
						<td>${ORDER.odto1.odate}</td>
					</tr>
				</tbody>
			</table>
		</div>
</div>
		<!-- 결제금액 -->
		<table style="margin: 40px 0 0 0;">
			<thead>
				<tr>
					<th colspan="2">상품금액</th>
					<th colspan="1"></th>
					<th colspan="1">배송비</th>
					<th colspan="1"></th>
					<th colspan="2">결제 금액</th>
				</tr>
			</thead>
			<tbody>
				<tr id="list" class="list">
					<td class="sum" colspan="2" ><h3>${ORDER.odto1.osum}원</h3></td>
					<td colspan="1" ><h3>+</h3></td>
					<td colspan="1" ><h3>무료</h3></td>
					<td colspan="1" ><h3>=</h3></td>
					<td class="sum" colspan="2" ><h3>${ORDER.odto1.osum}원</h3></td>
				</tr>
			</tbody>
		</table>
</body>
</html>