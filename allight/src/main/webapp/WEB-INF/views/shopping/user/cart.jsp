<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title></title>
	<!-- jQuery CDN -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/cart.js"></script>
	<style>
		.allCheck { float:left; width:110px; }
		.allCheck input { width:16px; height:16px; }
		.allCheck label { margin-left:10px; }
		.delBtn { float:left; width:300px; text-align:left; }
		.delBtn button { font-size:14px; padding:5px 10px; border:1px solid #eee; background:#eee;}
		
		.checkBox { float:left; width:30px; }
		.checkBox input { width:16px; height:16px; }
		
		.td:iamount div:itemInfo input { font-size:22px; width:50px; padding:5px; margin:0; border:1px solid #eee; }
 		.div.itemInfo button { font-size:26px; border:none; background:none; }
 		.list > td {text-align:center;}
	</style>
</head>
<body>
	<div class="title3">장바구니</div>
	<hr/>
		<div id="content">
			<c:if test="${!empty sessionScope.MID}">
				<div class="allCheck">
					<label for="allCheck">전체선택 </label>
					<input type="checkbox" name="allCheck" id="allCheck" checked="checked"/>
				</div>
				<div class="delBtn">
					<button type="button" class="selectDelete_btn">선택삭제</button>
				</div>
			</c:if>
			<table>
				<c:if test="${!empty sessionScope.MID}">
					<thead>
						<tr>
							<th scope="col">
								<div class="center">선택</div>
							</th>
							<th scope="col" colspan="2">
								<div class="center">상품정보</div>
							</th>
							<th scope="col">
								<div class="center">판매가</div>
							</th>
							<th scope="col">
								<div class="center">수량</div>
							</th>
							<th scope="col">
								<div class="center">합계</div>
							</th>
						</tr>
					</thead>
				</c:if>
				<tbody>
					<c:if test="${empty clist and empty sessionScope.MID}">
						<tr>
							<td colspan="6" class="center">
							<h3>로그인 또는 회원가입을 해주세요.</h3>
							<input type="button" value="로그인" onclick="location.href='${pageContext.request.contextPath}/login.com'" style="margin-top: 5px;">
							<input type="button" value="회원가입" onclick="location.href='${pageContext.request.contextPath}/joinFrm.com'" style="margin-top: 5px;">
						</td>
						</tr>
					</c:if>
					<c:if test="${empty clist and !empty sessionScope.MID}">
						<td colspan="6" class="center"><h3>${sessionScope.MNAME }님, 장바구니에 담긴 상품이 없습니다.</h3></td>
					</c:if>
					
					<c:set var="sum0" value="0" />
					<%-- <c:set var="qty" value="0" /> --%>
					<c:forEach var="list" items="${clist}">
						<input type="hidden" name="arr" id="cano" value="${list.cano}"/><!-- 주문페이지 연결시 cano보내주려고 -->
						<tr>
							<td scope="col"><!-- 체크박스 -->
								<div class="center">
									<input type="checkbox" class="chkBox" checked="checked" data-cartNo="${list.cano}"/>
								</div>
							</td>
							<!-- 상품정보 -->
							<td scope="col">
								<div class="tb-left">
									<img src="${list.imgimages}" style="height:200px; width:200px; overflow:hidden;" onerror="this.src='${pageContext.request.contextPath}/resources/img/no-img.png'" />
								</div>
							</td>
							<td>
								<div style="height:200px;  width:200px; padding:80px 0;"><a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${list.ino}" style="width:100px;">${list.iname }</a></div>
							</td>
							<!-- 판매가 -->
							<td scope="col"><!-- 판매가 -->
								<div class="center">
									<p class="price" style="height:200px; line-height:200px;margin:0;">
									<fmt:formatNumber pattern="#,###" value="${list.iprice }" />원
									</p>
								</div>
							</td>
							<!-- 수량 -->
							<td class="iamount" scope="col">
								<input type="hidden" id="origin_qty" value="${list.caamount }" maxlength="3"/>
								<div class="center" style="height:200px; line-height:200px;margin:0;">
									<button type="button" id="minus_btn" class="minus">-</button>
									<input type="text" id="number" class="numBox" value="${list.caamount }" readonly="readonly" 
										style="font-size:16px; width:50px; padding:5px; margin:0; border:1px solid #eee;"/>
									<button type="button" id="plus_btn" class="plus">+</button>
								</div>
							</td>
							<!-- 합계 -->
							<td class="total" scope="col">
								<div class="tb-center">
									<p class="total-price" style="height:200px; line-height:200px;margin:0;">
										<fmt:formatNumber pattern="#,###" value="${list.caprice}" />원
									</p>
								</div>
							</td>
						</tr>
					<c:set var="sum0" value="${sum0 + (list.iprice*list.caamount)}" />
					<%-- <c:set var="qty" value="${qty + list.caamount }" /> --%>
					</c:forEach>
				</tbody>
			</table>
			<hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/><hr/>
			<c:if test="${!empty sessionScope.MID}">
				<table>
					<thead>
						<tr>
							<th colspan="2">상품금액</th>
							<th colspan="1"></th>
							<th colspan="1">배송비</th>
							<th colspan="1"></th>
							<th colspan="2">결제 예정금액</th>
						</tr>
					</thead>
					<tbody>
						<tr id="list" class="list">
							<td class="sum" colspan="2" ><h3><fmt:formatNumber pattern="#,###" value="${sum0}" />원</h3></td>
							<td colspan="1" ><h3>+</h3></td>
							<td colspan="1" ><h3>무료</h3></td>
							<td colspan="1" ><h3>=</h3></td>
							<td class="sum" colspan="2" ><h3><fmt:formatNumber pattern="#,###" value="${sum0}" />원</h3></td>
						</tr>
					</tbody>
				</table>
	<form action="../buy.com" id="cart" method="post">
		<input type="hidden" name="canoList" id="canoList"/>
			<div class="center" style="margin:30px 20px">
				<input type="button" class="btn" value="계속 쇼핑" onclick="location.href='${pageContext.request.contextPath}/shopping/list.com'" />&nbsp;
				<input type="button" class="btn5" id="pay" value="결제하기"/>
			</div>
	</form>
			</c:if>
		</div>
</body>
</html>