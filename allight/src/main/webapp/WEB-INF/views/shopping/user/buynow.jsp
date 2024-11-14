<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
	<script type="text/javascript">
	
	var amount
	var bemail
	var bname
	var btel
	var baddr
	var bpc
	var payment 
	
	//취소버튼 누르면 뒤로가기
	function goBack(){
		window.history.back();
	}
	
	//모달창 시작
	$(function(){
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
	    
	    //모달창 띄울 버튼
	    $('#delivadd').click(function(){
			wrapCreateByMask();
		    $('body').css("overflow", "hidden");
		})
		
		//구매자 정보와 동일하게 입력 체크박스 누르면 이름,핸드폰번호 가져오기
		$("#namecheck").on('click',function(){
	    	if(document.getElementById("namecheck").checked==true){
	    		//alert("체크박스클릭함")
	    		$('#name').val('${sessionScope.MNAME}');
	    		$('#tel').val('${sessionScope.MTEL}');
	    		//alert("체크박스클릭함222")
	    	}
		})
    	//구매자 정보와 동일하게 입력 체크박스 해제하면 이름,핸드폰번호 지우기
		$("#namecheck").on('click',function(){
	    	if(document.getElementById("namecheck").checked==false){
	    		//alert("체크박스클릭함")
	    		$('#name').val('');
	    		$('#tel').val('');
	    		//alert("체크박스클릭함222")
	    	}
		})
		
		//배송메세지
		$('#delivreq1').change(function(){
			$('#delivreq1 option:selected').each(function(){
				if($(this).val()=='5'){
					$('#req1').val('');
					$('#req1').attr('disabled',false);
				}else{
					$('#req1').val($(this).text());
					$('#req1').attr('disabled',true);
				}
			})
		})
		
		//결제버튼 누르면
    	$("#paybtn").click(function(){
    		//이름 입력 여부
			if($("#name").val().length==0){
				alert("이름을 입력하지 않았습니다.입력해주세요.")
				$("#name").focus();
				return false;
			}
		    
		  	//연락처 입력 여부
			if($("#tel").val().length==0){
				alert("연락처를 입력하지 않았습니다.입력해주세요.")
				$("#tel").focus();
				return false;
			}
			//우편번호 입력 여부
			if($("#sample6_postcode").val().length==0){
				alert("우편번호를 입력하지 않았습니다.입력해주세요.")
				$("#sample6_postcode").focus();
				return false;
			}
			
			//주소 입력 여부
			if($("#sample6_address").val().length==0){
				alert("주소를 입력하지 않았습니다.입력해주세요.")
				$("#sample6_address").focus();
				return false;
			}
			
			//상세주소 입력 여부
			if($("#sample6_detailAddress").val().length==0){
				alert("상세주소를 입력하지 않았습니다.입력해주세요.")
				$("#sample6_detailAddress").focus();
				return false;
			}
			
			//받는사람 이름 가져오기
			$("#oreceiver").val($("#name").val())
			//배송요청사항 가져오기
			$("#orequirethings").val($("#req1").val())
			$('#buy').submit();
    	});
		
	});
	
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
   	
   	//주소 받아오기
   	function goAddress(aname,aphone,aaddno,aaddress1,aaddress2){
   		$('#name').val(aname);
   		$('#tel').val(aphone)
   		$('#sample6_postcode').val(aaddno);
   		$('#sample6_address').val(aaddress1);
   		$('#sample6_detailAddress').val(aaddress2);
   		$('#mask_create, .create_modal').hide();
       $('body').css("overflow", "scroll");
   	}
   	//모달창 끝
   	</script>
</head>
<body>
<h3>상품주문</h3>
<hr/>
<form action="./payNowSuccess.com" id="buy" name="buy" method="post">
	<!--  어두워지는 부분1  -->
	<div id="mask_create"></div>
	<!-- 모달 부분1 (칼로리 상세) -->
	<div class="create_modal">
		<div class="top" style="">
		   <div class="close" >x</div>
		</div>
		<div class="bottom">
			<!-- 배송지 팝업 -->
			<div>
				<!-- 목록출력 -->
				<table class="table">
					<tbody>
						<c:if test="${empty alist}">
							<tr>
								<th>등록된 배송지가 없습니다. 새로 입력해주세요.</th>
							</tr>
						</c:if>
						<c:if test="${!empty alist}">
								<tr>
									<th>수령인</th>
									<th>연락처</th>
									<th>우편번호</th>
									<th>주소</th>
									<th>선택</th>
								</tr>
							<c:forEach items="${alist}" var="list">
								<tr class="dataRow">
									<td>${list.aname}</td>
									<td>${list.aphone}</td>
									<td>${list.aaddno}</td>
									<td>${list.aaddress1} , ${list.aaddress2}</td>
						  			<td>
										<input type="button" id="cBtn" value="선택" onclick="goAddress('${list.aname}','${list.aphone}','${list.aaddno}','${list.aaddress1}','${list.aaddress2}')">	<!-- 선택누르면 js이용해서 view단 배송지로 보내기 -->
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="contents">	
		<!-- "주문페이지에서 장바구니 > 주문 > 결제 이 부분" 
		<div class="order_top_wrap">
			<div class="order_top">
				<div class="order_title1">주문/결제</div>
				<ul class="order_subtitle_list">
					<li>
						<div class="order_subtitle1"><h2>장바구니</h2></div>
					</li>
					<li>
						<div class="order_subtitle2 on"><h2>주문/결제</h2></div>
					</li>
					<li>
						<div class="order_subtitle3"><h2>결제완료</h2></div>
					</li>
				</ul>
			</div>
		</div> -->

		<!-- 주문페이지 상품 상세 시작 -->
		<table class="order_tbl" border="1" summary="주문 리스트">
			<thead>
				<tr>
					<th class="order_amount" colspan="3" scope="col">주문 상품 정보</th>
					<th class="order_amount" colspan="1"scope="col">수량</th>
					<th class="order_amount" colspan="1" >판매가</th>
					<th class="order_amount" colspan="1" scope="col">주문 금액</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="sum0" value="0" /><!-- 수량 set -->
					<tr>
						<td class="order_thmb" colspan="1"><!-- 상품이미지 -->
							<a><img src="${list.imgimages}" style="height:200px; width:200px; overflow:hidden;" onerror="this.src='${pageContext.request.contextPath}/resources/img/no-img.png'" /></a>
						</td>
						<td colspan="2"><!-- 상품정보 -->
							<div style="height:200px; line-height:200px;"><a class="iname" id="iname" href="${pageContext.request.contextPath}/shopping/detail.com?ino=${list.ino}">${list.iname}</a></div>
						</td>
						<input type="hidden" id="ino" name="ino" value="${list.ino}" />
						<input type="hidden" id="iname" name="iname" value="${list.iname}" />
						<td class="iamount" scope="col" colspan="1"> <!-- 장바구니 수량 -->
							<input type="hidden" id="origin_qty" name="caamount" value="${list.caamount}" maxlength="3"/>
							<input type="text" id="number" class="numBox" value="${list.caamount}" readonly="readonly" 
								style="font-size:16px; width:50px; padding:5px; margin:0; border:1px solid #eee;"/>
						</td>
						<td scope="col" colspan="1"><!-- 판매가 -->
							<div class="tb-center">
								<p class="price" style="height:200px; line-height:200px;margin:0;">
									<fmt:formatNumber pattern="#,###" value="${list.iprice}" />원
								</p>
							</div>
						</td>
						<td scope="col" colspan="1"><!-- 주문금액 -->
							<div class="tb-center">
								<p class="total" style="height:200px; line-height:200px;margin:0;">
									<fmt:formatNumber pattern="#,###" value="${list.caprice}" />원
									<input type="hidden" name="caprice" value="${list.caprice}"/>
								</p>
							</div>
						</td>
					</tr>
				<c:set var="sum0" value="${sum0 + (list.iprice*list.caamount)}" />
			</tbody>
		</table>

		<!-- 주문자 정보 영역 시작 -->
		<div class="order_detail mt80">
			<h3 class="order_detail_tit">주문자 정보</h3>
			<table>
				<tbody>
					<tr>
						<th style="width:150px" class="center" >이름</th>
						<td>${sessionScope.MNAME}</td>
						<%-- <input type="text" name="mname" value="${sessionScope.MNAME}" /> --%>
					</tr>
					<tr>
						<th style="width:150px" class="center">휴대폰 번호</th>
						<td>${sessionScope.MTEL}</td>
					</tr>
					<tr>
						<th style="width:150px" class="center">이메일</th>
						<td>${sessionScope.MEMAIL}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!--    배송지 정보 영역 시작 -->
		<div class="order_detail mt80">
			<h3 class="order_detail_tit">배송지 정보</h3>
			<table class="tbl" border="1" summary="정보">
				<tbody>
				<tr>
					<th>배송지선택</th>
					<td>
						<div id="addrradio">
							<!-- <input id="delivradio1" class="order_rdo" type="radio" onclick="" checked="checked" value="" name="delivaddradio">
							<label style="margin-right:15px">신규 배송지</label> -->
							<input type="button" id="delivadd" value="주소록에서 가져오기" name="delivadd">
						</div>
					</td>
				</tr>

				<tr>
					<th>받으실분</th><!-- 자바스크립트로  체크박스 선택하면 이름,연락처 받아오게 처리-->
					<td><input type="text" style="width:130px" name="name" id="name" maxlength="20" placeholder="받으실 분 성함 입력">&nbsp;&nbsp;
						<input type="checkbox" id="namecheck" > 구매자 정보와 동일하게 입력
						<input type="hidden" name="oreceiver" id="oreceiver"/>
					</td>
				</tr>
				<tr>
					<th>
					<span>연락처</span>
					</th>
					<td>
						<input class="order_txt order_dimmed" style="width:200px" type="text" name="otel" id="tel" maxlength="13">
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>
						<input type="text" name="oaddno" id="sample6_postcode" placeholder="우편번호">
						<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
						<input type="text" name="oaddress1" id="sample6_address" placeholder="주소" style="width:300px"><br>
						<input type="text" name="oaddress2" id="sample6_detailAddress" placeholder="상세주소">
						<input type="text" name="oaddress" id="sample6_extraAddress" placeholder="참고항목" style="width:100px">
						
						<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
						<script>
						    function sample6_execDaumPostcode() {
						        new daum.Postcode({
						            oncomplete: function(data) {
						                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
						
						                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
						                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						                var addr = ''; // 주소 변수
						                var extraAddr = ''; // 참고항목 변수
						
						                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
						                    addr = data.roadAddress;
						                } else { // 사용자가 지번 주소를 선택했을 경우(J)
						                    addr = data.jibunAddress;
						                }
						
						                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
						                if(data.userSelectedType === 'R'){
						                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
						                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
						                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
						                        extraAddr += data.bname;
						                    }
						                    // 건물명이 있고, 공동주택일 경우 추가한다.
						                    if(data.buildingName !== '' && data.apartment === 'Y'){
						                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
						                    }
						                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
						                    if(extraAddr !== ''){
						                        extraAddr = ' (' + extraAddr + ')';
						                    }
						                    // 조합된 참고항목을 해당 필드에 넣는다.
						                    document.getElementById("sample6_extraAddress").value = extraAddr;
						                
						                } else {
						                    document.getElementById("sample6_extraAddress").value = '';
						                }
						
						                // 우편번호와 주소 정보를 해당 필드에 넣는다.
						                document.getElementById('sample6_postcode').value = data.zonecode;
						                document.getElementById("sample6_address").value = addr;
						                // 커서를 상세주소 필드로 이동한다.
						                document.getElementById("sample6_detailAddress").focus();
						            }
						        }).open();
						    }
						</script>
					</td>
				</tr>
				<tr class="last">
					<th>
					<span>배송요청사항</span>
					</th>
					<td>
						<div class="order_select_box" style="width:300px">
							<select class="order_select" style="width:300px" name="delivreq1" id="delivreq1">
								<option value="0" selected="selected">선택하세요</option>
								<option value="1">부재시 핸드폰으로 연락바랍니다.</option>
								<option value="2">부재시 경비실에 맡겨주세요.</option>
								<option value="3">빠른 배송 부탁드립니다.</option>
								<option value="4">배송전 핸드폰으로 연락바랍니다.</option>
								<option value="5">직접 입력</option>
							</select>
							<span class="order_select_arr"></span>
						</div>
						<p class="mt5">
						<textarea class="tbl_addr_txt" name="req1" id="req1" style="width:392px" maxlength="50" cols="50" rows="5" placeholder="(직접입력)최대 50자까지 입력가능합니다." >
						</textarea>
						<input type="hidden" name="orequirethings" id="orequirethings" />
						<span class="s fc999 byteCnt">(최대 50자)</span>
						</p><div class="order">
							<p class="order_notice">※ "배송요청사항"은 택배기사님들이 확인하는 메시지로써, 배송 연기 요청 등<br> 판매자에게 전달할 요청사항은 꼭! 고객센터로 연락주세요.</p>
						</div>
						<p></p>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		
		<!--결제 정보 영역 시작 -->
		<!--결제금액박스-->
		<div>
			<h3>총 주문금액</h3>
		</div>
		<table>
			<thead>
				<tr>
					<th colspan="2">주문금액</th>
					<th colspan="1"></th>
					<th colspan="1">배송비</th>
					<th colspan="1"></th>
					<th colspan="2">총 결제금액</th>
				</tr>
			</thead>
			<tbody>
				<tr id="list" class="list">
					<td class="sum" colspan="2" ><h3><fmt:formatNumber pattern="#,###" value="${sum0}" />원</h3></td>
					<td colspan="1" ><h3>+</h3></td>
					<td colspan="1" ><h3>무료</h3></td>
					<td colspan="1" ><h3>=</h3></td>
					<td class="sum" id="sum" colspan="2" ><h3><fmt:formatNumber pattern="#,###" value="${sum0}"/>원</h3></td>
					<input type="hidden" name="osum" value="${sum0}"/>
				</tr>
			</tbody>
		</table>

		<!--  결제하기/ 취소 버튼 영역 시작 -->
		<div class="center">
			<input type="button" id="cacbtn" value="취소" onclick="goBack();" />
			<input type="button" id="paybtn" value="결제하기"/>
		</div>
</div>
</form>
</body>
</html>