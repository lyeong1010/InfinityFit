<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/list.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.point-on:hover{
	background: #eee;
	cursor: pointer;
}
</style>
<script type="text/javascript">
var gram; //상세 - 초기 gram
var kcal; //상세 - 초기 kcal
var tan0; //상세 - 초기 tan(g) 
var dan0; //상세 - 초기 dan(g)
var ji0; //상세 - 초기 ji(g)
var sik0; //상세 - 초기 sik(g)
var na0; //상세 - 초기 na(g)
var tan; //상세 - 초기 tan(%)
var dan; //상세 - 초기 dan(%)
var ji; //상세 - 초기 ji(%)
var sik; //상세 - 초기 sik(%)
var na; //상세 - 초기 na(%)
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
  
    //닫기 버튼을 눌렀을 때2
    $('.create_modal2 .close').click(function(e) {
       //링크 기본동작은 작동하지 않도록 한다.
       e.preventDefault();
       $('#mask_create2, .create_modal2').hide();
       $('body').css("overflow", "scroll");
    });
 
    //검은 막을 눌렀을 때2
    $('#mask_create2').click(function() {
       $(this).hide();
       $('.create_modal2').hide();
       $('body').css("overflow", "scroll");
    });
    
    // 칼로리 등록 버튼 눌렀을 때
    $('#enrollBtn').click(function(){
       var fcdnos = [];
       // 선언한 배열에 선택한 cdno 넣기
       $("input[name=checkbox]:checked").each(function() { 
            fcdnos.push(Number($(this).next('input').val()));
        });
       
       $('#cdtype').val($('#cdtypes').val());
       $('#cdnos').val(fcdnos);
       $('#insertFoodFrm').submit();
    });
    
   // 이름누르면 체크되게
    $('.cdname').click(function(){
       var checkbox = $(this).closest('td').prev('td').children('input[name=checkbox]');
       checkbox.attr('checked', !checkbox.is(':checked'));
    })
    
    // 상세 모달에서 수량 바뀌면 분량(g),칼로리,그래프 변화
    $('#amount').change(function(){
       $('#gram').val(gram*$(this).val());
       $('#kcal').val(Math.floor(kcal*$('#gram').val()/gram));
       
       var ttan0 = Math.round(tan0*$(this).val()*100)/100;
       var ddan0 = Math.round(dan0*$(this).val()*100)/100;
       var jji0 = Math.round(ji0*$(this).val()*100)/100;
       var ssik0 = Math.round(sik0*$(this).val()*100)/100;
       var nna0 = Math.round(na0*$(this).val()*100)/100;
       var ttan = Math.round(tan*$(this).val()*100)/100;
       var ddan = Math.round(dan*$(this).val()*100)/100;
       var jji = Math.round(ji*$(this).val()*100)/100;
       var ssik = Math.round(sik*$(this).val()*100)/100;
       var nna = Math.round(na*$(this).val()*100)/100;
       
       $('#tan').text(ttan0+'('+ttan+'%)');
       $('#dan').text(ddan0+'('+ddan+'%)');
       $('#ji').text(jji0+'('+jji+'%)');
       $('#sik').text(ssik0+'('+ssik+'%)');
       $('#na').text(nna0+'('+nna+'%)');
       
       $('#t-bar > div > div').css('width',ttan+'%');
       $('#d-bar > div > div').css('width',ddan+'%');
       $('#j-bar > div > div').css('width',jji+'%');
       $('#s-bar > div > div').css('width',ssik+'%');
       $('#n-bar > div > div').css('width',nna+'%');
    })
    
    // 상세 모달에서 분량(g) 바뀌면 칼로리,그래프 변화
   $('#gram').change(function(){
      $('#kcal').val(Math.floor(kcal*$('#gram').val()/gram));
      
       var ttan0 = Math.round(tan0*$(this).val()*100/gram)/100;
       var ddan0 = Math.round(dan0*$(this).val()*100/gram)/100;
       var jji0 = Math.round(ji0*$(this).val()*100/gram)/100;
       var ssik0 = Math.round(sik0*$(this).val()*100/gram)/100;
       var nna0 = Math.round(na0*$(this).val()*100/gram)/100;
       var ttan = Math.round(tan*$(this).val()*100/gram)/100;
       var ddan = Math.round(dan*$(this).val()*100/gram)/100;
       var jji = Math.round(ji*$(this).val()*100/gram)/100;
       var ssik = Math.round(sik*$(this).val()*100/gram)/100;
       var nna = Math.round(na*$(this).val()*100/gram)/100;
       
       $('#tan').text(ttan0+'('+ttan+'%)');
       $('#dan').text(ddan0+'('+ddan+'%)');
       $('#ji').text(jji0+'('+jji+'%)');
       $('#sik').text(ssik0+'('+ssik+'%)');
       $('#na').text(nna0+'('+nna+'%)');
       
       $('#t-bar > div > div').css('width',ttan+'%');
       $('#d-bar > div > div').css('width',ddan+'%');
       $('#j-bar > div > div').css('width',jji+'%');
       $('#s-bar > div > div').css('width',ssik+'%');
       $('#n-bar > div > div').css('width',nna+'%');
   })
   
   $('#detail-submit').click(function(){
      $('#detailFrm').submit();
   })
   
   $('#goBack').click(function(){
      document.location.href = "./myFAE.com?num=${DTO.dno}";
   })
   
   $('#makeMyBtn').click(function(){
      wrapCreateByMask2();
       $('body').css("overflow", "hidden");
   })
   
   $('#insertMyBtn').click(function(){
      var data = $("#insertMyCalFrm").serialize() ;
      
        $.ajax({
            type : 'post',
            url : './insertMyCal.com',
            data : data,
            error: function(xhr, status, error){
                alert(error);
            },
            success : function(json){
                location.reload();
            },
        });

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
function wrapCreateByMask2() {
   // 화면의 높이와 너비를 구한다.
   var maskHeight = $(document).height();
   var maskWidth = $(window).width();

   // 마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
   $('#mask_create2').css({
      'width' : maskWidth,
      'height' : maskHeight
   });

   $('#mask_create2').fadeTo("slow", 1);

   $('.create_modal2').show();
};
   
function detail(cdno,cdname,cdgram,cdcal,cdtan,cddan,cdji,cdsik,cdna){
   tan0 = Number(cdtan);
   dan0 = Number(cddan);
   ji0 = Number(cdji);
   sik0 = Number(cdsik);
   na0 = Number(cdna);
   tan = Math.round(cdtan*100/300);
   dan = Math.round(cddan*100/70);
   ji = Math.round(cdji*100/40);
   sik = Math.round(cdsik*100/20);
   na = Math.round(cdna*100/1500);
   
   $('#amount').val(1);
   $('#detail-cdno').val(cdno);
   $('#name').text(cdname);
   $('#gram').val(cdgram);
   $('#kcal').val(cdcal);
   $('#tan').text(cdtan+'('+tan+'%)');
   $('#dan').text(cddan+'('+dan+'%)');
   $('#ji').text(cdji+'('+ji+'%)');
   $('#sik').text(cdsik+'('+sik+'%)');
   $('#na').text(cdna+'('+na+'%)');
   
   $('#t-bar > div > div').css('width',tan+'%');
   $('#d-bar > div > div').css('width',dan+'%');
   $('#j-bar > div > div').css('width',ji+'%');
   $('#s-bar > div > div').css('width',sik+'%');
   $('#n-bar > div > div').css('width',na+'%');
   
   gram = cdgram;
   kcal = cdcal;
   
   wrapCreateByMask();
    $('body').css("overflow", "hidden");
}

function deleteMy(cdno) {
   $.ajax({
        type : 'post',
        url : './deleteMyCal.com',
        data : {"cdno":cdno},
        error: function(xhr, status, error){
            alert(error);
        },
        success : function(json){
            alert("성공")
            location.reload();
        },
    });
}

</script>
<style type="text/css">
.bar {
  width:300px;
  height: 8px;
  background: #777;
  margin: 1em;
}

.bar div {
  width: 0;
  max-width:300px;
  height: 100%;
  background: #fc3232;
}
</style>
</head>
<body>
<div id="wrap2">
   <div class="title2">칼로리 사전</div>
   <div class="diary-content">
      <form id="MFsearchFrm">
          <input type="text" name="searchWord" class="calTxt" placeholder="검색" value="${WORD}"/>
          <button type="submit" class="calBtn">
             <img src="${pageContext.request.contextPath}/resources/img/search.png" class="shopSearchImg"/>
            </button>
            <input type="hidden" name="dno" value="${DTO.dno}"/>
            <input type="hidden" name="dno" value="${DTO.ddate}"/>
         </form>
      
      
        <!--  
         <div class="search-divs1">
            <div class="f-left">
               검색결과 300개
            </div>
          
            <div class="f-right">
               <select id="cdtypes">   
                  <option value="아침">아침</option>
                  <option value="점심">점심</option>
                  <option value="저녁">저녁</option>
                  <option value="간식">간식</option>
               </select>
               <input type="button" value="칼로리 등록" class="btn" id="enrollBtn"/>
               <input type="button" value="마이칼로리 직접입력" class="btn" id="makeMyBtn"/>
            </div>
         </div>
         -->
         
         
         <div class="search-divs2">
            <ul>
               <li class="search-div-on"><a style="cursor:pointer;" onclick href="${pageContext.request.contextPath}/cal/dictionary/food.com">음식 사전</a></li>
               <li class="search-div"><a style="cursor:pointer;" onclick href="${pageContext.request.contextPath}/cal/dictionary/exercise.com">운동 사전</a></li>
            </ul>
      </div>
      
      <table class="search-table" id="caldic-table">
         <c:forEach var="list" items="${LIST}">
         <tr class="point-on" onclick="detail(${list.cdno},'${list.cdname}',${list.cdgram},${list.cdcal},${list.cdtan},${list.cddan},${list.cdji},${list.cdsik},${list.cdna})">
            <td width="5%"><input type="hidden" name="cdno" value="${list.cdno}"></td>
            <td width="60%" class="cdname">${list.cdname}&nbsp;&nbsp;(${list.cdamount}회, ${list.cdgram}g)</td>
            <td width="25%" align="right">${list.cdcal}kcal</td>
            
         </tr>
         </c:forEach>
         
         <c:if test="${empty LIST}">
            <tr>
               <td class="center">검색된 내용이 없습니다.</td>
            </tr>
         </c:if>
      </table>
      
      <!-- 
      <table class="search-table" id="mycal-table">
         <c:forEach var="list" items="${MYLIST}">
         <tr>
            <td width="5%"><input type="checkbox" name="checkbox"/><input type="hidden" name="cdno" value="${list.cdno}"></td>
            <td width="60%" class="cdname">${list.cdname}&nbsp;&nbsp;(${list.cdamount}, ${list.cdgram}g)</td>
            <td width="25%" align="right">${list.cdcal}kcal</td>
            <td width="5%" onclick="detail(${list.cdno},'${list.cdname}',${list.cdgram},${list.cdcal},${list.cdtan},${list.cddan},${list.cdji},${list.cdsik},${list.cdna})">&gt;</td>
            <td width="5%" onclick="deleteMy(${list.cdno})">X</td>
         </tr>
         </c:forEach>
         <c:if test="${empty MYLIST}">
            <tr>
               <td class="center">검색된 내용이 없습니다.</td>
            </tr>
         </c:if>
      </table>
       -->
       
      <!-- 페이징 -->
      <c:if test="">
      </c:if>
      <div class="center">
         <ul class="pagination" id="Page">
            <li>
               <c:if test="${PINFO.nowPage > 3}">
                  <a href="${pageContext.request.contextPath}/cal/dictionary/food.com?nowPage=${PINFO.nowPage-3}&searchWord=${searchWord}">«</a>
               </c:if>
               <c:if test="${PINFO.nowPage <= 3}">
                  <a href="${pageContext.request.contextPath}/cal/dictionary/food.com?nowPage=1">«</a>
               </c:if>
            </li>
            <!-- 현재 페이지일때 active --> 
            <c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
               <li id="li"><!-- 스크립트 적용해야 할것같아요 -->
                  <a href="${pageContext.request.contextPath}/cal/dictionary/food.com?nowPage=${i}&searchWord=${searchWord}">${i}</a>
               </li>
            </c:forEach>            
            <li>
               <c:if test="${PINFO.nowPage < PINFO.endPage-3}">
                  <a href="${pageContext.request.contextPath}/cal/dictionary/food.com?nowPage=${PINFO.nowPage+3}&searchWord=${searchWord}">»</a>
               </c:if>
               <c:if test="${PINFO.nowPage >= PINFO.endPage-2}">
                  <a href="${pageContext.request.contextPath}/cal/dictionary/food.com?nowPage=${PINFO.endPage}&searchWord=${searchWord}">»</a>
               </c:if>
            </li>
         </ul>
      </div>
      
      
      
      
   </div>
</div>

<!--<!--  어두워지는 부분1  -->
<div id="mask_create"></div>
<!-- 모달 부분1 (칼로리 상세) -->
<div class="create_modal">

<div class="top" style="">
   <div class="close" >x</div>
</div>

<div class="bottom">
<form class="diary-div" action="./insertMyFood2.com" id="detailFrm" method="post">
   <div class="title2">음식</div>
   <div class="diary-content">
      <div id="name" class="detail-name-div"></div>
      <input type="hidden" name="dno" value="${DTO.dno}"/>
      <input type="hidden" name="cdno" id="detail-cdno"/>
      <table id="detail-table">
          <tr>
          <!-- 
             <td>구분</td>
              -->
             <td>
             <!-- 
                <select name="cdtype">   
                  <option value="아침">아침</option>
                  <option value="점심">점심</option>
                  <option value="저녁">저녁</option>
                  <option value="간식">간식</option>
               </select>
                -->
            </td>
          </tr>
         <tr>
            <td>수량</td>
            <td><input type="number" value="1" min="1" id="amount" name="mfamount"/></td>
         </tr>
         <tr>
            <td>분량(g)</td>
            <td><input type="number" id="gram" min="0" name="mfgram"/></td>
         </tr>
         <tr>
            <td>칼로리(kcal)</td>
            <td><input type="number" id="kcal" min="0" name="mftotalcal"/></td>
         </tr>
         <tr>
            <td colspan="2">
               <table class="detail-in-table">
                  <tr>
                     <td>탄수화물(g)</td>
                     <td id="tan" class="right"></td>
                     <td id="t-bar" width="40%"><div class="bar"><div></div></div></td>
                  </tr>
                  <tr>
                     <td>단백질(g)</td>
                     <td id="dan" class="right"></td>
                     <td id="d-bar"><div class="bar"><div></div></div></td>
                  </tr>
                  <tr>
                     <td>지방(g)</td>
                     <td id="ji" class="right"></td>
                     <td id="j-bar"><div class="bar"><div></div></div></td>
                  </tr>
                  <tr>
                     <td>식이섬유(g)</td>
                     <td id="sik" class="right"></td>
                     <td id="s-bar"><div class="bar"><div></div></div></td>
                  </tr>
                  <tr>
                     <td>나트륨(mg)</td>
                     <td id="na" class="right"></td>
                     <td id="n-bar"><div class="bar"><div></div></div></td>
                  </tr>
                  <tr>
                     <td colspan="3">
                        *(%)영양성분 기준치 : 1일 영샹성분 기준치에 대한 비율<br/>
                        *2,000kcal 기준이므로 개인에 따라 다를 수 있습니다.
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
      </table>
      <div class="center" style="margin:30px 0;">
      <!-- 
         <input type="button" value="칼로리 등록" id="detail-submit"/>
         <input type="button" value="마이칼로리에 추가" id="insertItMyBtn"/>
          -->
         <input type="button" value="닫기" class="close"/>
      </div>
   </div>
</form>
</div> 
</div> 

<!-- 어두워지는 부분2 (마이칼로리 추가) -->
<div id="mask_create2"></div>
<!-- 모달 부분2 (마이칼로리 추가) -->
<div class="create_modal2">

<div class="top" style="">
   <div class="close" >x</div>
</div>

<div class="bottom">
<form class="diary-div" id="insertMyCalFrm" method="post"> 
   <div class="title2">음식</div>
   <div class="diary-content">
      <div id="name" class="my-name-div">
         <input type="text" name="cdname" placeholder="음식명을 입력해주세요." class="myCalText">
      </div>
      <input type="hidden" name="num" value="${DTO.dno}"/>
      <table id="detail-table">
          <tr>
            <td>수량</td>
            <td><input type="number" value="1" name="cdamount"/></td>
            <td>탄수화물</td>
            <td><input type="number" min="0" name="cdtan"/></td>
         </tr>
         <tr>
            <td>분량(g)</td>
            <td><input type="number"min="0" name="cdgram"/></td>
            <td>단백질</td>
            <td><input type="number" min="0" name="cddan"/></td>
         </tr>
         <tr>
            <td>칼로리(kcal)</td>
            <td><input type="number" min="0" name="cdcal"/></td>
            <td>지방</td>
            <td><input type="number" min="0" name="cdji"/></td>
         </tr>
         <tr>
            <td colspan="2"></td>
            <td>식이섬유</td>
            <td><input type="number" min="0" name="cdsik"/></td>
         </tr>
         <tr>
            <td colspan="2"></td>
            <td>나트륨</td>
            <td><input type="number" min="0" name="cdna"/></td>
         </tr>
      </table>
      <div class="center" style="margin:30px 0;">
         <input type="button" value="마이칼로리에 추가" id="insertMyBtn"/>
         <input type="button" value="취소" class="close"/>
      </div>
   </div>
</form>
</div>
</div>

<form method="post" action="./insertMyFood.com" id="insertFoodFrm">
   <input type="hidden" name="dno" value="${DTO.dno}"/>
   <input type="hidden" name="cdnos" id="cdnos"/>
   <input type="hidden" name="cdtype" id="cdtype"/>
</form>

</body>
</html>