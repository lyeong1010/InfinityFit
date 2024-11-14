<%@ page language="java" contentType="text/html" 
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>

function myFAE(day,dno){
	if(${!empty sessionScope.MID}){
		$('#num').val(dno);
		$('#year').val(${today_info.search_year});
		$('#month').val(${today_info.search_month});
		$('#day').val(day);
		$('#myFAEFrm').submit();
	}else{
		if(${!empty sessionScope.COID}){
			alert('일반회원만 사용가능한 서비스입니다.')
		}else if(confirm('로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?')){
			location.href = "./login.com";
		}
	}
};

function chart(yy,mon){
	if(${!empty sessionScope.MID}){
		location.href='./chart.com?yy='+yy+'&mon='+mon;
	}else{
		if(${!empty sessionScope.COID}){
			alert('일반회원만 사용가능한 서비스입니다.')
		}else if(confirm('로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?')){
			location.href = "./login.com";
		}
	}
};

$(function(){
	// 성공/실패 div 배경색 바꾸기
	$('.success').closest('.main-imgs-div').closest('div').closest('td').css('background','#e0f7ff');
	$('.fail').closest('.main-imgs-div').closest('div').closest('td').css('background','#ffe8df');
	
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
    
 	// help 버튼 눌렀을 때
	$('.helpBtn').click(function(){
		wrapCreateByMask();
	    $('body').css("overflow", "hidden");
	})
})

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
</script>
<style type="text/css">
	.today, .sat_day, .sun_day, .normal_day {
		cursor:pointer;
	}
</style>
</head>

<body>
<div id="wrap">
	<a class="title">다이어리
		<input type="button" value="월별 다이어트 추이  &gt;" class="goto" onclick="chart(${today_info.search_year},${today_info.search_month});" style="background:white;"/>
		<input type="button" value="Today" onclick="location.href='${pageContext.request.contextPath}/main.com'" class="btn4" style="float:right;height:45px;margin:0 15px;"/>
		<input type="button" value="?" class="helpBtn"/>
	</a>
	
	<form name="calendarFrm" id="calendarFrm" action="" method="GET">
	
		<div class="calendar" id="calendar">
		
			<!--날짜 네비게이션  -->
			<div class="main-nav">
				<!-- 이전해 -->
				<a class="before_after_year" href="./main.com?year=${today_info.search_year-1}&month=${today_info.search_month-1}">
					&lt;&lt;
				</a> 
				<!-- 이전달 -->
				<a class="before_after_month" href="./main.com?year=${today_info.before_year}&month=${today_info.before_month}">
					&lt;
				</a> 
				<!-- 이번달 -->
				<span class="this_month">
					&nbsp;&nbsp;${today_info.search_year}. 
					<c:if test="${today_info.search_month<10}">0</c:if>${today_info.search_month}&nbsp;&nbsp;
				</span>
				<!-- 다음달 -->
				<a class="before_after_month" href="./main.com?year=${today_info.after_year}&month=${today_info.after_month}">
					&gt;
				</a> 
				<!-- 다음해 -->
				<a class="before_after_year" href="./main.com?year=${today_info.search_year+1}&month=${today_info.search_month-1}">
					&gt;&gt;
				</a>
			</div>
		
			<table class="calendar_body">
			<thead>
				<tr bgcolor="#CECECE" class="main-thead-tr">
					<td class="day sun" >일</td>
					<td class="day" >월</td>
					<td class="day" >화</td>
					<td class="day" >수</td>
					<td class="day" >목</td>
					<td class="day" >금</td>
					<td class="day sat" >토</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach var="dateList" items="${dateList}" varStatus="date_status"> 
						<c:choose>
							<c:when test="${dateList.value=='today'}">
								<td class="today" onclick="myFAE(${dateList.day},${dateList.dno});">
									<div class="date">
										${dateList.day}
									</div>
									<c:if test="${dateList.day!=''}">
										<div class="main-imgs-div">
											<c:if test="${dateList.dfoodcal ne 0 or dateList.dexercal ne 0 or dateList.dweight ne 0 or !empty dateList.ddiary or !empty dateList.dimage}">
												<c:if test="${dateList.dsucc eq 1 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
													<div class="success"></div>
												</c:if>
												<c:if test="${dateList.dsucc eq 0 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
													<div class="fail"></div>
												</c:if>
												<div>
													<img src="${pageContext.request.contextPath}/resources/img/cutlery.png">
													<div class="main-text-div">${dateList.dfoodcal}</div>
												</div>
												<div>
													<img src="${pageContext.request.contextPath}/resources/img/dumbbell.png">
													<div class="main-text-div">${dateList.dexercal}</div>
												</div>
												<div>
													<img src="${pageContext.request.contextPath}/resources/img/scale.png">
													<div class="main-text-div">${dateList.dweight}</div>
												</div>
											</c:if>
										</div>
									</c:if>
								</td>
							</c:when>
							<c:when test="${date_status.index%7==6}">
								<c:if test="${!empty dateList.day}">
									<td class="sat_day" onclick="myFAE(${dateList.day},${dateList.dno});">
								</c:if>
								<c:if test="${empty dateList.day}">
									<td>
								</c:if>
										<div class="sat">
											${dateList.day}
										</div>
										<c:if test="${dateList.day!=''}">
											<div class="main-imgs-div">
												<c:if test="${dateList.dfoodcal ne 0 or dateList.dexercal ne 0 or dateList.dweight ne 0 or !empty dateList.ddiary or !empty dateList.dimage}">
													<c:if test="${dateList.dsucc eq 1 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
														<div class="success"></div>
													</c:if>
													<c:if test="${dateList.dsucc eq 0 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
														<div class="fail"></div>
													</c:if>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/cutlery.png">
														<div class="main-text-div">${dateList.dfoodcal}</div>
													</div>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/dumbbell.png">
														<div class="main-text-div">${dateList.dexercal}</div>
													</div>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/scale.png">
														<div class="main-text-div">${dateList.dweight}</div>
													</div>
												</c:if>
											</div>
										</c:if>
									</td>
							</c:when>
							<c:when test="${date_status.index%7==0}">
				</tr>
				<tr>	
								<c:if test="${!empty dateList.day}">
									<td class="sun_day" onclick="myFAE(${dateList.day},${dateList.dno});">
								</c:if>
								<c:if test="${empty dateList.day}">
									<td>
								</c:if>	
										<div class="sun">
											${dateList.day}
										</div>
										<c:if test="${dateList.day!=''}">
											<div class="main-imgs-div">
												<c:if test="${dateList.dfoodcal ne 0 or dateList.dexercal ne 0 or dateList.dweight ne 0 or !empty dateList.ddiary or !empty dateList.dimage}">
													<c:if test="${dateList.dsucc eq 1 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
														<div class="success"></div>
													</c:if>
													<c:if test="${dateList.dsucc eq 0 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
														<div class="fail"></div>
													</c:if>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/cutlery.png">
														<div class="main-text-div">${dateList.dfoodcal}</div>
													</div>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/dumbbell.png">
														<div class="main-text-div">${dateList.dexercal}</div>
													</div>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/scale.png">
														<div class="main-text-div">${dateList.dweight}</div>
													</div>
												</c:if>
											</div>
										</c:if>
									</td>
							</c:when>
							<c:otherwise>
								<c:if test="${!empty dateList.day}">
									<td class="normal_day" onclick="myFAE(${dateList.day},${dateList.dno});">
								</c:if>
								<c:if test="${empty dateList.day}">
									<td>
								</c:if>
										<div class="date">
											${dateList.day}
										</div>
										<c:if test="${dateList.day!=''}">
											<div class="main-imgs-div">
												<c:if test="${dateList.dfoodcal ne 0 or dateList.dexercal ne 0 or dateList.dweight ne 0 or !empty dateList.ddiary or !empty dateList.dimage}">
													<c:if test="${dateList.dsucc eq 1 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
														<div class="success"></div>
													</c:if>
													<c:if test="${dateList.dsucc eq 0 and (dateList.dexercal ne 0 or dateList.dfoodcal ne 0)}">
														<div class="fail"></div>
													</c:if>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/cutlery.png">
														<div class="main-text-div">${dateList.dfoodcal}</div>
													</div>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/dumbbell.png">
														<div class="main-text-div">${dateList.dexercal}</div>
													</div>
													<div>
														<img src="${pageContext.request.contextPath}/resources/img/scale.png">
														<div class="main-text-div">${dateList.dweight}</div>
													</div>
												</c:if>
											</div>
										</c:if>
									</td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</tbody>
			</table>
		</div>
	</form>
</div>

<!--<!--  어두워지는 부분  -->
<div id="mask_create"></div>
<!-- 모달 부분 (도움말) -->
<div class="create_modal">
<div class="top" style="">
   <div class="close">x</div>
</div>
<div class="bottom">
<img src="${pageContext.request.contextPath}/resources/img/help-img.png" style="width:100%"/>
</div> 
</div> 

<form action="./myFAE.com" method="post" id="myFAEFrm">
	<input type="hidden" name="num" id="num"/>
	<input type="hidden" name="year" id="year"/>
	<input type="hidden" name="month" id="month"/>
	<input type="hidden" name="day" id="day"/>
</form>
</body>
</html>
