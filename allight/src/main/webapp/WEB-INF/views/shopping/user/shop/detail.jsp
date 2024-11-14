<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑상세</title>
<script type="text/javascript">

// 맨 아래/위로
function goTop(){
	window.scrollTo(0,0);
}
function goBottom(){
	document.documentElement.scrollTop = document.body.scrollHeight;
}

//수량 선택시 값 변경
var sell_price;
var amount;

function init () {
	sell_price = document.form.sell_price.value;
	amount = document.form.amount.value;
	document.form.sum.value = sell_price;
	change();
}
function add () {
	hm = document.form.amount;
	sum = document.form.sum;
	hm.value ++ ;

	sum.value = parseInt(hm.value) * sell_price;
}
function del () {
	hm = document.form.amount;
	sum = document.form.sum;
		if (hm.value > 1) {
			hm.value -- ;
			sum.value = parseInt(hm.value) * sell_price;
		}
}
function change () {
	hm = document.form.amount;
	sum = document.form.sum;

		if (hm.value < 0) {
			hm.value = 0;
		}
	sum.value = parseInt(hm.value) * sell_price;
}  

// 로그인 여부 확인/로그인창 보내기
function loginCheck (){
	if('${sessionScope.MID}'==''){
		if(confirm('로그인 후 이용가능한 서비스입니다.\n로그인하시겠습니까?')){
			$('#reUrlFrm').submit();
		}
	}
}

// 천단위 콤마
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 상문 문의 삭제
function iqDelete(iqno) {
	if(confirm('정말 삭제하시겠습니까?')){
		var iqno = iqno;
		
		$.ajax({
	    	url: '${pageContext.request.contextPath}/shopping/iqDelete.com',
	        data: {iqno:iqno},
	        method: 'post',
	        success: function(data){
	        	alert('삭제 완료했습니다.');
	        	location.reload();
	        },
			error:function(request,status,error){
            	alert('삭제 실패했습니다.\n잠시 후 다시 시도해주세요.')
            	location.reload();
				//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	    })
	}
}

// 상품 문의 수정
function iqModify(){
	var $target = $(event.target);
	//alert($target.nodeName)
	$target.closest('td').attr('class','hidden');
	$target.closest('td').next('td').attr('class','show');
}

//비밀글일때 작성자 본인 체크
function isWriter(iqid){
	var iqid = iqid;
	if(iqid != '${sessionScope.MID}'){
		alert("작성자 본인만 확인 가능합니다.")
	}
}

$(function(){
	
	// 네비게이션
	$(window).scroll(function(){  //스크롤하면 아래 코드 실행
       	var scroll = $(this).scrollTop();  // 스크롤값
        if( scroll > 890 ){  // 스크롤을 890이상 했을 때
        	$(".pdt_detai_tabinner_vn").attr("class","pdt_detai_tabinner_vn2");
        }else{
    	    $(".pdt_detai_tabinner_vn2").attr("class","pdt_detai_tabinner_vn");
        }
       	
       	var detail = $('#detail').offset().top;
       	var review = $('#review').offset().top;
       	var question = $('#question').offset().top;
       
        if(scroll >= detail && scroll < review-10){
    		$('.pdt_detai_tabinner_vn ul li:nth-child(1)').attr('class','pdt_item fir on');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(1)').attr('class','pdt_item fir on');
    		$('.pdt_detai_tabinner_vn ul li:nth-child(2)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(2)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn ul li:nth-child(3)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(3)').attr('class','pdt_item');
    	}
    	if(scroll >= review-10 && scroll < question){
    		$('.pdt_detai_tabinner_vn ul li:nth-child(1)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(1)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn ul li:nth-child(2)').attr('class','pdt_item fir on');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(2)').attr('class','pdt_item fir on');
    		$('.pdt_detai_tabinner_vn ul li:nth-child(3)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(3)').attr('class','pdt_item');
    	}
    	if(scroll >= question-10){
    		$('.pdt_detai_tabinner_vn ul li:nth-child(1)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(1)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn ul li:nth-child(2)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(2)').attr('class','pdt_item');
    		$('.pdt_detai_tabinner_vn ul li:nth-child(3)').attr('class','pdt_item fir on');
    		$('.pdt_detai_tabinner_vn2 ul li:nth-child(3)').attr('class','pdt_item fir on');
    	}
  	});
	
	//장바구니로 넘어가기(로그인 안하면 에러나게 하고싶음!)
	$('.in-cart-btn').click(function(){
		if(confirm("장바구니에 담으시겠습니까?")){
			var ino = Number(${param.ino});
			var caamount = Number($('#number').val());
			var caprice = Number($('.right-total-price').text().replace(',',''));
			
			$.ajax({
				  type : 'post',
	              url : '${pageContext.request.contextPath}/insertCart.com',
	              data : {"ino":ino,
	                    "caamount":caamount,
	                    "caprice":caprice},
	              success : function(result){
	                  if( result =="success"){
	                	  if(confirm("장바구니에 담겼습니다.\n장바구니 페이지로 이동하시겠습니까?")){
	                	  	location.href="${pageContext.request.contextPath}/mypage/cart.com";
						  }
	                  }else if( result == "fail"){
	                	  if(confirm("회원만 이용 가능한 서비스입니다.\n로그인 하시겠습니까?")){
	                	  	location.href="../login.com"
	                	  }
	                  }
	              },
	              error: function(xhr, status, error){
	                  alert("요청을 처리할 수 없습니다. 다시 시도해주세요.");
	              },
			})
		};
	})
	
	//바로구매하기로 넘어가기
	$('.purchase-btn').click(function(){
		if(confirm("바로 구매하시겠습니까?")){
			var mid = $("#mid").val();
			if(mid === null || mid === ''){
				if(confirm("회원만 이용 가능한 서비스입니다.\n로그인 하시겠습니까?")){
					location.href="../login.com"
				}
			}else{
				var caamount = Number($('#number').val());
				var caprice = Number($('.right-total-price').text().replace(',',''));
				$("#caamount").attr('value',caamount);
				$("#caprice").attr('value',caprice);
				$("#buynow").submit();
			}
		};
	});
	
	// 수량 선택시 총 상품가격 변동
	$('#number').change(function(){
		$('.right-total-price').text(
			numberWithCommas($(this).val()*${DETAIL.iprice}))
	})
	
	// 상품문의 제목 클릭시 내용 보이기
	$('.iqtitle').click(function(){
		if($(this).parent().parent().next('.hidden').attr('class')=='hidden'){
			$(this).parent().parent().next('.hidden').attr('class','show');
			$(this).parent().parent().next('.hidden').next('.hidden').attr('class','show');
			return false;
		}else {
			$(this).parent().parent().next('.show').attr('class','hidden');
		}
	})
	
	// 상품문의 작성 버튼 클릭시 작성 form 보이기
	$('#iqWriteBtn').click(function(){
		if($('#iqWriteFrm').attr('class')=='hidden'){
			$('#iqWriteFrm').attr('class','show');
			$('#qTable').attr('class','hidden');
			return false;
		}else {
			$('#iqWriteFrm').attr('class','hidden');
			$('#qTable').attr('class','show');
		}
	})
	
	// 상품후기/문의 현재 페이지일 경우 li에 active 클래스 적용
	$('#rPage').children().each(function(){
		if($(this).children('a').text()==${RPINFO.nowPage}){
			$(this).attr('class','active');
		}
	})
	$('#qPage').children().each(function(){
		if($(this).children('a').text()==${QPINFO.nowPage}){
			$(this).attr('class','active');
		}
	})
	
	// 상품 문의 등록 제출/취소
	$('#writeSubmit').click(function(){
		if($('#iqtitle').val()=='' || $('#iqtitle').val()==null){
			alert('제목을 입력하세요.')
			return false;
		}
		
		if($('#iqcontent').val()=='' || $('#iqcontent').val()==null){
			alert('내용을 입력하세요.')
			return false;
		}
		
		if(confirm('상품문의를 등록하시겠습니까?')){
			$('#iqWriteFrm').submit();
		}
	})
	$('#writeCancel').click(function(){
		if(confirm('상품문의 등록을 취소하시겠습니까?')){
			$('#iqWriteFrm').attr('class','hidden');
			$('#qTable').attr('class','show');
		}
	})
	
	// 상품 문의 수정 제출/취소
	$('.modifySubmit').click(function(){
		$target = $(event.target);
		var $content = $target.prev('.iqModicontent');
		var $title = $target.closest('form').children('table').children('tbody').children('tr').eq(0).children('td').eq(1).children('input');
		
		if($title.val()=='' || $title.val()==null){
			alert('제목을 입력하세요.')
			return false;
		}
		
		if($content.val()=='' || $content.val()==null){
			alert('내용을 입력하세요.')
			return false;
		}
		
		if(confirm('상품문의를 수정하시겠습니까?')){
			$target.closest('form').submit();
		}
	})
	$('.modifyCancel').click(function(){
		var $td = $(event.target).parent().parent().closest('td');
		if(confirm('상품문의 수정을 취소하시겠습니까?')){
			$td.prev('td').attr('class','show');
			$td.attr('class','hidden');
		}
	})
	
});
</script>
</head>

<body>
	<!-- 맨 위로/아래로 버튼 -->
	<div class="top_wrap" style="right: 50px;">
		<div class="top_box_wrap">
			<div class="top_box">
				<a onclick="goTop();" style="cursor:pointer">
					<p class="top_txt">
						<img src="${pageContext.request.contextPath}/resources/img/arrow-top.png" alt="top">
					</p>
				</a>
				<a onclick="goBottom();" style="cursor:pointer">
					<p class="top_txt">
						<img src="${pageContext.request.contextPath}/resources/img/arrow-bottom.png" alt="down">
					</p>
				</a>		
			</div>
		</div>
	</div>
	
	<!-- 쇼핑 상세보기 본문  -->
	<div class="shopDiv">
	<div class="container">
		<div class="shop-head-title">${DETAIL.icategory} > </div>
		<div class="card">
			<div class="card-img">
				<img src="${REPREIMG}"/>
			</div>
			<div class="card-txt">
				<h1 class="info-name">${DETAIL.iname}</h1>
				<p class="info-detail">${DETAIL.idetail}</p>
				<table>
					<tr>
						<td class="card-info-td">판매가격</td>
						<td class="card-info-td0">
							<fmt:formatNumber value="${DETAIL.iprice}" pattern="#,###"/>원
						</td>
					</tr>
					<tr>
						<td class="card-info-td">브랜드</td>
						<td class="card-info-td1">${DETAIL.icorp}</td>
					</tr>
					<tr>
						<td class="card-info-td">배송방법</td>
						<td class="card-info-td1">업체배송</td>
					</tr>
					<tr>
						<td class="card-info-td">배송비</td>
						<td class="card-info-td0">무료</td>
					</tr>
				</table>
				<div style="height:10px;"></div>
				<hr/>
				<a class="left-how-many">수량:
					<input type="number" min="1" value="1" id="number">
				</a>
				<a class="right-one">원</a>
				<a class="right-total-price">
					<fmt:formatNumber value="${DETAIL.iprice}" pattern="#,###"/>
				</a>
				<a class="right-total">총 상품금액</a>
				
				<input type="button" value="장바구니" class="in-cart-btn"/>
				<input type="button" value="구매하기" class="purchase-btn" id="purchase-btn"/>
				<form action="${pageContext.request.contextPath}/buyNow.com" method="post" id="buynow">
				<input type="hidden" id="mid" value="${sessionScope.MID}" >
				<input type="hidden" name="ino" value="${param.ino}">
				<input type="hidden" name="caamount" id="caamount">
				<input type="hidden" name="caprice" id="caprice">
				<input type="hidden" name="imgimages" value="${REPREIMG}">
				<input type="hidden" name="iname" value="${DETAIL.iname}">
				<input type="hidden" name="iprice" value="${DETAIL.iprice}">
				</form>
			</div>
			<div id="detail"></div>
		</div>
			
		<!-- 네비게이션  -->
		<div class="pdt_detai_tabinner_vn">
			<ul>
				<li class="pdt_item"><a href="#detail" style="cursor:pointer;">상세설명</a></li>
				
				<li class="pdt_item"><a href="#review" style="cursor:pointer;">상품후기(${RSIZE})</a></li>
				
				<li class="pdt_item"><a href="#question" style="cursor:pointer;">상품문의(${QSIZE})</a></li>
			</ul>
		</div>
		
		<!-- 상세 이미지  -->
		<div class="details">
			<div class="detail-imgs">
				<c:forEach var="img" items="${IMGS}">
					<img src="${img}"/>
					<br/>
				</c:forEach>
				<c:if test="${empty RLIST}">
						<div class="center">상세 이미지가 존재하지 않습니다.</div>
				</c:if>
			</div>
		</div>
		
		<!-- 상품후기/상품문의  -->
		<div class="review-qustion-div">
		
			<!-- 상품후기   -->
			<div id="review" style="height:75px;"></div>
			<div style="height:100px;"></div>
			<div class="shop-mini-title">
				상품후기(${RSIZE})
			</div>
	
			<div class="boardContent-Comment-Table">
				<table>
					<c:forEach var="list" items="${RLIST}">
						<tr>
							<td colspan="100%" class="board-comment-info">
								<a class="board-info-nick">
									<c:forEach begin="1" end="${list.rgrade}">★</c:forEach><c:forEach begin="1" end="${5-list.rgrade}">☆</c:forEach>
								</a>&nbsp;&nbsp; 
								<a class="board-info-others">|&nbsp;&nbsp;${list.rnick}</a>
							</td>
						</tr>
						<tr>
							<td width="80%"><pre>${list.rcontent}</pre></td>
							<td style="padding: 0; text-align: center;">
								<c:if test="${empty sessionScope.MID}">
									<a onclick="loginCheck();" style="cursor:pointer;"> 
										<img class="like" src="${pageContext.request.contextPath}/resources/img/like.png" />
									</a>
								</c:if>
								<c:if test="${!empty sessionScope.MID}">
									<a href="./reviewLike.com?ino=${DETAIL.ino}&rno=${list.rno}&rNowPage=${RPINFO.nowPage}"> 
										<c:if test="${list.isLiked}">
											<img class="like" src="${pageContext.request.contextPath}/resources/img/liked.png" />
										</c:if>
										<c:if test="${!list.isLiked}">
											<img class="like" src="${pageContext.request.contextPath}/resources/img/like.png" />
										</c:if>
									</a>
								</c:if>
								<a class="aNone">${list.rlamount}</a>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty RLIST}">
						<tr>
							<td colspan="100%" class="center">
								상품후기가 존재하지 않습니다.
							</td>
						</tr>
					</c:if>
				</table>
				<c:if test="${!empty RLIST}">
					<div class="center">
						<ul class="pagination" id="rPage">
				            <li>
				               <c:if test="${RPINFO.nowPage > 3}">
				                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&rNowPage=${RPINFO.nowPage-3}#review">«</a>
				               </c:if>
				               <c:if test="${RPINFO.nowPage <= 3}">
				                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&rNowPage=1#review">«</a>
				               </c:if>
				            </li>
				            <!-- 현재 페이지일때 active --> 
				            <c:forEach begin="${RPINFO.startPage}" end="${RPINFO.endPage}" var="i">
				               <li><!-- 스크립트 적용해야 할것같아요 -->
				                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&rNowPage=${i}#review">${i}</a>
				               </li>
				            </c:forEach>            
				            <li>
				               <c:if test="${RPINFO.nowPage < RPINFO.endPage-3}">
				                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&rNowPage=${RPINFO.nowPage+3}#review">»</a>
				               </c:if>
				               <c:if test="${RPINFO.nowPage >= RPINFO.endPage-3}">
				                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&rNowPage=${RPINFO.endPage}#review">»</a>
				               </c:if>
				            </li>
				        </ul>
					</div>
				</c:if>
			</div>
			
			
			<!-- 상품문의   -->
			<div id="question" style="height:75px;"></div>
			<div style="height:100px;"></div>
			<c:if test="${empty sessionScope.COID}">
				<div class="float-right">
					<c:if test="${empty sessionScope.MID}">
						<a class="btn" onclick="loginCheck();">문의하기</a>
					</c:if>
					<c:if test="${!empty sessionScope.MID}">
						<a class="btn" id="iqWriteBtn">문의하기</a>
					</c:if>
				</div>	
			</c:if>
			<div class="shop-mini-title">
				상품문의(${QSIZE})
			</div>
			
			<form action="${pageContext.request.contextPath}/shopping/iqWrite.com" method="post" id="iqWriteFrm" class="hidden">
				<div class="shop-mini-title">
					상품문의 작성
				</div>
				<table>
					<tbody>	
						<tr>
							<td width="15%">제목</td>
							<td>
								<input type="text" name="iqtitle" id="iqtitle"/>
							</td>	
						</tr>
						<tr>
							<td>비밀글 설정</td>
							<td>
								<input type="checkbox" name="iqsecret" value="1" />
							</td>
						</tr>
						<tr>								
							<td colspan="2">
								<textarea name="iqcontent" id="iqcontent" rows="10" cols="200" 
									style="margin:10px 0;padding:10px;width:100%;height:300px;"></textarea>
							</td>
						</tr>					
					</tbody>
				</table>
				<input type="hidden" name="ino" value="${param.ino}"/>
				<input type="hidden" name="iqid" value="${sessionScope.MID}"/>
				<input type="hidden" name="iqnick" value="${sessionScope.MNICK}"/>
				<input type="button" value="등록" id="writeSubmit"/>
				<input type="button" value="취소" id="writeCancel"/>
			</form>
			
			<div id="qTable">
			<table>
				<tr>
					<th style="border-bottom:1px solid gray; width:7%">번호</th>
					<th style="border-bottom:1px solid gray; width:53%" colspan="2">제목</th>
					<th style="border-bottom:1px solid gray; width:20%">작성일</th>
					<th style="border-bottom:1px solid gray; width:20%">닉네임</th>
				</tr>
				<c:forEach var="list" items="${QLIST}">
					<tr>
						<td class="center">${list.iqno}</td>
						<td colspan="2">
							<c:if test="${list.iqsecret==1}">
								<img class="like" src="${pageContext.request.contextPath}/resources/img/secret.png" />
							</c:if>
							<c:if test="${list.iqsecret==1}">
								<a class="iqtitle" onclick="isWriter('${list.iqid}')">
									&nbsp;${list.iqtitle}&nbsp;
									<c:if test="${!empty list.iqccontent}">[답변완료]</c:if>
								</a>
							</c:if>
							<c:if test="${list.iqsecret!=1}">
								<a class="iqtitle">
									&nbsp;${list.iqtitle}&nbsp;
									<c:if test="${!empty list.iqccontent}">[답변완료]</c:if>
								</a>
							</c:if>
						</td>
						<td class="center">${list.iqdate}</td>
						<td class="center">${list.iqnick}</td>
					</tr>
					<c:if test="${!empty list.iqcontent}">
						<tr class="hidden">
							<td colspan="5" style="padding:0 0 5px;">
								<table>
									<tr>
										<td colspan="5" style="padding:40px 20px">
											<c:if test="${sessionScope.MID eq list.iqid}">
												<div class="float-right-box">
													<a onclick="iqModify();">수정&nbsp;</a>
													|
													<a onclick="iqDelete(${list.iqno});">삭제</a>
												</div>
											</c:if>
											<pre>${list.iqcontent}</pre>
										</td>
										<td colspan="5" style="padding:0;" class="hidden">
											<form action="./iqModify.com" class="modifyFrm">
												<table class="ques-mody-table">
													<tr>
														<td width="10%" class="modi-table-th">제목</td>
														<td><input type="text" name="iqtitle" class="iqModititle" value="${list.iqtitle}"></td>
													</tr>
													<tr>
														<td class="modi-table-th">비밀글 설정</td>
														<td>
															<c:if test="${list.iqsecret==1}">
																<input type="checkbox" name="iqsecret" value="1" checked="checked"/>
															</c:if>
															<c:if test="${list.iqsecret!=1}">
																<input type="checkbox" name="iqsecret" value="1" />
															</c:if>
														</td>
													</tr>
													<tr>
														<td colspan="2" style="text-align:center;">
															<textarea name="iqcontent" class="iqModicontent" rows="5" cols="200"
															>${list.iqcontent}</textarea>
															<input type="button" value="수정" class="modifySubmit"/>
															<input type="button" value="취소" class="modifyCancel"/>
														</td>
														
													</tr>
												</table>
												<input type="hidden" name="ino" value="${DETAIL.ino}" />
												<input type="hidden" name="iqno" value="${list.iqno}" />
											</form>
										</td>
									</tr>
									
									<c:if test="${!empty list.iqccontent}">
										<tr>
											<td colspan="2" style="padding:30px 20px">${list.iqcnick}</td>
											<td colspan="2" style="padding:30px 20px;width:65%;">${list.iqccontent}</td>
											<td class="center" style="padding:30px 20px">${list.iqcdate}</td>
										</tr>
									</c:if>
								</table>
							</td>
						</tr>	
					</c:if>
				</c:forEach>
				<c:if test="${empty QLIST}">
					<tr><td colspan="5" class="center">상품문의가 존재하지 않습니다.</td></tr>
				</c:if>
			</table>
			<c:if test="${!empty QLIST}">
				<div class="center">
					<ul class="pagination" id="qPage">
			            <li>
			               <c:if test="${QPINFO.nowPage > 3}">
			                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&qNowPage=${QPINFO.nowPage-3}#question">«</a>
			               </c:if>
			               <c:if test="${QPINFO.nowPage <= 3}">
			                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&qNowPage=1#question">«</a>
			               </c:if>
			            </li>
			            <!-- 현재 페이지일때 active --> 
			            <c:forEach begin="${QPINFO.startPage}" end="${QPINFO.endPage}" var="i">
			               <li><!-- 스크립트 적용해야 할것같아요 -->
			                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&qNowPage=${i}#question">${i}</a>
			               </li>
			            </c:forEach>            
			            <li>
			               <c:if test="${QPINFO.nowPage < QPINFO.endPage-3}">
			                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&qNowPage=${QPINFO.nowPage+3}#question">»</a>
			               </c:if>
			               <c:if test="${QPINFO.nowPage >= QPINFO.endPage-3}">
			                  <a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}&qNowPage=${QPINFO.endPage}#question">»</a>
			               </c:if>
			            </li>
			        </ul>
				</div>
			</c:if>
			</div>
		</div>
	</div>
	</div>
	<form action="${pageContext.request.contextPath}/login.com" id="reUrlFrm">
		<input type="hidden" name="reUrl" value="${pageContext.request.contextPath}/shopping/detail.com?ino=${param.ino}">
	</form>

</body>
</html>