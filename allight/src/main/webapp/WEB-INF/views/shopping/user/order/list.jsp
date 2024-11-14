<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
#mask_create {
position: absolute;
z-index: 9000;
background-color: #444;
display: none;
left: 0;
top: 0;
}

.create_modal {
   overflow-y: scroll;
   z-index: 10000;
    width: 1000px;
    height: auto;
    max-height:800px;
    background-color: white;
    margin:0 auto;
    display:none;
    position: fixed;
    left: 0;
    right:0;
    top: 50%;
   -webkit-transform:translateY(-50%);
   -moz-transform:translateY(-50%);
   -ms-transform:translateY(-50%);
   -o-transform:translateY(-50%);
   transform:translateY(-50%);
}

.create_modal .top{ 
width:100%; height:5%; 
border-radius:12px 12px 0px 0px;
}

.create_modal .top .close {
float: right;
cursor: pointer;
color: black;
font-size: 25px;
font-weight: bold;
padding-top: 5px;
padding-right: 20px;
}

.create_modal .top .subject{
float:left;
margin-left:10px;
margin-top:20px;
font-size:20px;
font-weight:bold;
color:white;
padding-left: 20px;
}

.create_modal .bottom{
width:100%; 
height:15%; 
vertical-align: middle;

}

.create_modal .bottom .info{
padding:10px 15px 10px 15px;
text-align: right;
color: red;
}

.create_modal .bottom .contents{
margin:15px 20px 15px 35px;
width:100%;
text-align: center;
}

.create_modal .bottom .contents th{
text-align: right;
}

.create_modal .bottom .contents .board_name{
width:236px;
height:28px;
}

.create_modal .bottom .contents .select{
width:240px;
height:35px;
font-size:15px;
}

.create_modal .bottom .insert_button {
width:375px;
height:40px;
font-size:15px;
background-color:gray;
color:white;
}
.create_modal .bottom .center{
margin-left:60px;
}
</style>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(function() {
		//취소 버튼 클릭 시
		$(".cancel")
				.click(
						function() {
							if (confirm("해당 상품을 주문 취소 하시겠습니까?")) {
								var odno = $(event.target).attr('data-no');
								var ostatus = "주문취소";
								var param = {
									"odno" : odno,
									"ostatus" : ostatus
								};
								//alert(JSON.stringify(param));
								$
										.ajax({
											url : "${pageContext.request.contextPath}/order/mypage/check.com",
											type : 'post',
											data : param,
											success : function(data) {
												location.href = "${pageContext.request.contextPath}/order/mypage/check.com?odno="
														+ odno
														+ "&ostatus="
														+ ostatus;
											},
											error : function(request, status,
													error) {
												alert("code:" + request.status
														+ "\n" + "message:"
														+ request.responseText
														+ "\n" + "error:"
														+ error);
											}
										});
							}
						});
		//반품 버튼 클릭 시
		$(".back")
				.click(
						function() {
							if (confirm("해당 상품을 반품 하시겠습니까?")) {
								var odno = $(event.target).attr('data-no');
								var ostatus = "반품";
								var param = {
									"odno" : odno,
									"ostatus" : ostatus
								};
								//alert(JSON.stringify(param));
								$
										.ajax({
											url : "${pageContext.request.contextPath}/order/mypage/check.com",
											type : 'post',
											data : param,
											success : function(data) {
												//alert(JSON.stringify(param));
												location.href = "${pageContext.request.contextPath}/order/mypage/check.com?odno="
														+ odno
														+ "&ostatus="
														+ ostatus;
											},
											error : function(request, status,
													error) {
												alert("code:" + request.status
														+ "\n" + "message:"
														+ request.responseText
														+ "\n" + "error:"
														+ error);
											}
										});
							}
						});
		//구매확정 버튼 클릭 시
		$(".confirm").click(function() {
			if (confirm("해당 상품을 구매확정 하시겠습니까? \n 구매확정 후에는 주문 취소 및 반품을 할 수 없습니다.")) {
				var odno = $(event.target).attr('data-no');
				var ostatus = "구매확정";
				var param = {
					"odno" : odno,
					"ostatus" : ostatus
				};
				$.ajax({
					url : "${pageContext.request.contextPath}/order/change.com",
					type : 'post',
					data : param,
					success : function(data) {
						location.href = "${pageContext.request.contextPath}/order/mypage/list.com";
					},
					error : function(request, status,error) {
						alert("code:" + request.status
								+ "\n" + "message:"
								+ request.responseText
								+ "\n" + "error:"
								+ error);
					}
				});
			}
		});
		
	})

	function delivery_view(com,tel) {
		var $target = $(event.target);
		var com = com;
		var tel = tel;
		//alert("com"+com+"tel"+tel);
		wrapCreateByMask();
		$('#com').text(com);
		$('#tel').text(tel);
	}
	function delivery_layer_view() {
		var $target = $(event.target);
		$target.closest('td').attr('class', 'hidden');
		$target.closest('td').next('td').attr('class', 'show');
	}
	
	function wrapCreateByMask() {
		   // 화면의 높이와 너비를 구한다.
		   var maskHeight = $(document).height();
		   var maskWidth = $(window).width();

		   // 마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
		   $('#mask_create').css({
		      'width' : maskWidth,
		      'height' : maskHeight
		   });

		   $('#mask_create').fadeTo("slow", 1);

		   $('.create_modal').show();
		};

	$(function() {
	   //검은 막 띄우기
	   //기본 모달창
	   $('.openMask_create').click(function(e) {
	      //클릭 시 이벤트
	      console.log("모달 켭니다.");
	      e.preventDefault();
	      wrapCreateByMask();
	      $('body').css("overflow", "hidden");
	   });

	   //닫기 버튼을 눌렀을 때
	   $('.create_modal .close').click(function(e) {
	      //링크 기본동작은 작동하지 않도록 한다.
	      e.preventDefault();
	      $('#mask_create, .create_modal').hide();
	      $('body').css("overflow", "scroll");
	   });

	   //검은 막을 눌렀을 때
	   $('#mask_create').click(function() {
	      $(this).hide();
	      $('.create_modal').hide();
	      $('body').css("overflow", "scroll");
	   });

	});
</script>
</head>
<body>

<!--  어두워지는 부분 -->
<div id="mask_create"></div>
<!--  모달 부분 -->
<div class="create_modal" >

<div class="bottom">
	<!-- 배송 팝업 -->
	<div id="order_pop_wrap">
		<div >
			<div >
				<h3>배송정보</h3>
				<div id="delivery_info_view"  class="order_detail">
					<table class="tbl" cellspacing="0" border="1" summary="정보">
						<colgroup>
							<col width="120">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<th>택배회사</th>
								<td><strong id="com"></strong></td>
							</tr>
							<tr>
								<th>송장번호</th>
								<td><em class="order_fcT2" id="tel"></em></td>
							</tr>
						</tbody>
					</table>
				</div>
				<p class="center">
					<a onclick="delivery_layer_view();" class="close"
						style="cursor: pointer;">닫기</a>
				</p>
			</div>
		</div>
	</div>
</div>
</div>
	<div>
		<!--마이페이지 내용 영역-->
		<!--타이틀-->
		<div>
			<div>
				<div class="title3">주문/배송 조회</div>
			</div>

			<div>
				<!--기간설정-->
				<ul>
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=w">1주일</a></li>
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=m1">1개월</a></li>
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=m3">3개월</a></li>
					<li style="margin: 0 10px;display: inline;" class="btn"><a href="?term=m6">6개월</a></li>
				</ul>
			</div>
		</div>
		<!--//타이틀-->

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
					<th colspan="2" scope="col">상품정보</th>
					<th scope="col">수량</th>
					<th scope="col">주문 금액</th>
					<th scope="col">진행 상태</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>


		<table>
			<colgroup>
				<col width="120">
				<col width="auto">
				<col width="140">
				<col width="200">
			</colgroup>
			<c:if test="${empty ORDER}">
				<div class="listno_tab noto_sans">주문내역이 없습니다.</div>
			</c:if>
			<c:forEach items="${ORDER.odto}" var="odto">
				<thead>
					<tr>
						<th colspan="5" scope="col">
							<div>
								<strong>주문번호&nbsp;<a href="./detail.com?no=${odto.ono}"><em>${odto.ordernum}</em></a></strong><em
									>(${odto.sodate})</em> <span><a href="./detail.com?no=${odto.ono}"
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
												> <img class="product-image"
													src="${sdto.imgimage}" ></a></td>
											<td><a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${sdto.ino}">${sdto.iname}</a>
												<ul>
													<li>${sdto.iprice}</li>
												</ul>
												<td><p style="margin: 40px 0">X ${oddto.odamount}</p></td>
												<td><p style="margin: 40px 0">= ${oddto.odprice}원</p></td>
											<td ><c:if
													test="${oddto.ostatus eq '주문취소' or oddto.ostatus eq '반품'}">
													<li style="font-weight:bold;margin: 10px 0 10px 15px;">${oddto.ostatus}</li>
												</c:if> <!-- 결제완료, 배송준비중 --> <c:if
													test="${oddto.ostatus eq '배송준비중' or oddto.ostatus eq '결제완료'}">
													<ul>
														<li style="font-weight:bold;margin: 10px 0 10px 15px;">${oddto.ostatus}</li>
														<li><a class="cancel" style="cursor: pointer;"
															data-no="${oddto.odno}">주문취소</a></li>
													</ul>
												</c:if> <!-- 배송시작, 배송완료 --> <c:if
													test="${oddto.ostatus eq '배송시작' or oddto.ostatus eq '배송완료'}">
													<ul>
														<li style="font-weight:bold;margin: 0 0 10px 15px;">${oddto.ostatus}</li>
														<li ><a onclick="delivery_view('${oddto.ocouriercompany}','${oddto.oinvoicenumber}');"
															class="btn" style="cursor: pointer;">배송조회</a></li>

														<li ><a class="confirm" style="cursor: pointer;"
															data-no="${oddto.odno}">구매확정</a></li>
														<li><a class="back" style="cursor: pointer;"
															data-no="${oddto.odno}">반품</a></li>
													</ul>
												</c:if> <!-- 구매확정 --> <c:if test="${oddto.ostatus eq '구매확정'}">
													<ul>
														<li style="font-weight:bold;margin: 0 0 10px 15px;">${oddto.ostatus}</li>

														<li ><a onclick="delivery_view('${oddto.ocouriercompany}','${oddto.oinvoicenumber}');"
															class="btn" style="cursor: pointer;">배송조회</a></li>
														<c:if test="${oddto.okreview ==0}">
														<li style="height: 50px;"><a
															href="review.com?no=${sdto.ino}&num=${oddto.odno}"
															class="btn" style="cursor: pointer;height: 50px;" >상품 </br>리뷰쓰기</a></li>
														</c:if>
													</ul>
												</c:if></td>
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