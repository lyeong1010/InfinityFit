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
			//검색
			$("#searb").click(
					function() {
						var form = $("#searchF").serialize();
						$.ajax({
							url : "${pageContext.request.contextPath}/order/mypage/back.com?term=${param.term}",
							type : 'get',
							data : form,
							success : function(data) {
								location.href = "${pageContext.request.contextPath}/order/mypage/back.com?term=${param.term}&"
										+ form;
							},
							error : function(xhr, status) {
								alert(xhr + " : " + status);
							}
					});
			});
		})
</script>
</head>
<body>
	<div>
		<!--마이페이지 내용 영역-->
		<!--타이틀-->
		<div>
			<div>
				<div class="title3">주문 취소/반품 조회</div>
			</div>

			<div class="searchDiv">
				<!-- 검색전 -->
				<c:if test="${empty param.type}">
					<form id="searchF">
						<select name="type" class="selectCss">
							<option value="cancel">주문 취소</option>
							<option value="back">반품</option>
						</select> <input type="button" id="searb" value="검색">
					</form>
				</c:if>
				<!-- 검색후 -->
				<c:if test="${!empty param.type}">
					<form id="searchF">
						<c:if test="${param.type eq 'cancel'}">
							<select name="type" class="selectCss">
								<option value="cancel" selected="selected">주문 취소</option>
								<option value="back">반품</option>
							</select>
						</c:if>
						<c:if test="${param.type eq 'back'}">
							<select name="type" class="selectCss">
								<option value="cancel">주문 취소</option>
								<option value="back" selected="selected">반품</option>
							</select>
						</c:if>
						<input type="button" id="searb" value="검색">
					</form>
				</c:if>
			</div>

			<div >
				<!--기간설정-->
				<ul >
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=w">1주일</a></li>
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=m1">1개월</a></li>
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=m3">3개월</a></li>
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=m6">6개월</a></li>
				</ul>
			</div>
		</div>
		<!--//타이틀-->

		<table>
			<thead>
				<tr>
					<th class="order_amount" colspan="2" scope="col">상품정보</th>
					<th class="order_amount" scope="col">수량</th>
					<th class="order_amount" scope="col">주문 금액</th>
					<th class="order_amount" scope="col">진행 상태</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ORDER.odto == null}">
					<tr>
						<td colspan="5" style="text-align: center;"><div
								class="listno_tab noto_sans">주문내역이 없습니다.</div></td>
					</tr>
				</c:if>
			</tbody>
		</table>


		<table>
			<c:if test="${empty ORDER}">
				<div class="listno_tab noto_sans">주문내역이 없습니다.</div>
			</c:if>
			<c:forEach items="${ORDER.odto}" var="odto">
				<thead>
					<tr>
						<th colspan="10" scope="col">
							<div class="center">
								<strong>주문번호&nbsp;<a href="./detail.com?no=${odto.ono}"
									><em class="order_fcT1">${odto.ordernum}</em></a></strong><em
									>(${odto.sodate})</em> <span
									><a href="./detail.com?no=${odto.ono}"
									>주문상세보기 &gt;</a></span>
							</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ORDER.oddto}" var="oddto">
						<c:if test="${odto.ono eq oddto.ono}">
							<tr class="last">
								<c:set var="done" value="false" />
								<c:forEach items="${ORDER.sdto}" var="sdto">
									<c:if test="${not done}">
										<c:if test="${sdto.ino eq oddto.ino}">
											<td><a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${sdto.ino}"
												> <img alt="temp_thmb"
													src="${sdto.imgimage}" class="product-image"></a></td>
											<td colspan="2"><a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${sdto.ino}"
											>${sdto.iname}</a>
												<ul>
													<li>${sdto.iprice}</li>
												</ul></td>
												<td><p style="margin: 40px 0;width:40px">X ${oddto.odamount}</p></td>
												<td><p style="margin: 40px 0;width:80px">= ${oddto.odprice}원</p></td>
											<form id="frm">
												<input type="hidden" name="no" value="${oddto.odno}" /> <input
													type="hidden" name="type" id="type" />
											</form>
											<td>
												<ul>
													<li style="font-weight:bold;margin: 10px 0 10px 15px;width:100px">${oddto.ostatus}</li>
												</ul>
											</td>
											<c:set var="done" value="true" />
										</c:if>
									</c:if>
								</c:forEach>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</c:forEach>
		</table>
	</div>
</body>
</html>