<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <style>
	.highcharts-figure, .highcharts-data-table table {
	  min-width: 310px;
	  max-width: 800px;
	  margin: 1em auto;
	}
	
	#container1 {
	  height: 400px;
	}
	
	.highcharts-data-table table {
		font-family: Verdana, sans-serif;
		border-collapse: collapse;
		border: 1px solid #EBEBEB;
		margin: 10px auto;
		text-align: center;
		width: 100%;
		max-width: 500px;
	}
	
	.highcharts-data-table caption {
	  padding: 1em 0;
	  font-size: 1.2em;
	  color: #555;
	}
	.highcharts-data-table th {
		font-weight: 600;
	  padding: 0.5em;
	}
	.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
	  padding: 0.5em;
	}
	.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
	  background: #f8f8f8;
	}
	.highcharts-data-table tr:hover {
	  background: #f1f7ff;
	}
	
	.nav {
	    text-align: center;
	    color: black;
	    padding: 15px;
	    font-size: 35px;
	    font-weight: bold;
	    font-family: auto;
	}
	
	.nav a {
	    color: black;
	    padding: 5px;
	    font-size: 35px;
	    font-weight: bold;
	}
	</style>
    <script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/export-data.js"></script>
	<script src="https://code.highcharts.com/modules/accessibility.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script>
	function nextmonth(yy,mon){
		var today = new Date();  
		var year = today.getFullYear(); // 년도
		var month = today.getMonth() + 1;  // 월
		if(yy >= year && mon >= month){
			alert("다음 달로 이동할 수 없습니다.")
			return false;
		}else{
			$('#nextmon').attr('href','./chart.com?yy='+${param.yy}+'&mon='+${param.mon+1})
		}
	}
	function nextyear(yy,mon){
		var today = new Date();  
		var year = today.getFullYear(); // 년도
		var month = today.getMonth()+1;  // 월
		if(yy >= year){
			alert("다음 해로 이동할 수 없습니다.")
			return false;
		}else if(parseInt(yy)+1 == year && mon > month){
			alert("다음 해로 이동할 수 없습니다.")
			return false;
		}else{
			$('#nextyy').attr('href','./chart.com?yy='+${param.yy+1}+'&mon='+${param.mon})
		}
	}
	$(function(){
		var today = new Date();  
		var year = today.getFullYear(); // 년도
		var month = today.getMonth()+1;  // 월
		if(${param.yy} > year || ${param.mon} > month){
			alert("해당 페이지를 이용할 수 없습니다.")
			location.href="./chart.com?yy="+year+"&mon="+month
		}
	})
	
	function change(){
		var type=$("#type option:selected").val();
		if(type=='w'){//몸무게
			$('#container1').attr('class','show')
			$('#container2').attr('class','hidden')
			$('#container3').attr('class','hidden')
			$('#container4').attr('class','hidden')
		}else if(type == 'f'){//음식
			$('#container1').attr('class','hidden')
			$('#container2').attr('class','show')
			$('#container3').attr('class','hidden')
			$('#container4').attr('class','hidden')
		}else if(type =='e'){//운동
			$('#container1').attr('class','hidden')
			$('#container2').attr('class','hidden')
			$('#container3').attr('class','show')
			$('#container4').attr('class','hidden')
		}else if(type =='s'){//운동
			$('#container1').attr('class','hidden')
			$('#container2').attr('class','hidden')
			$('#container3').attr('class','hidden')
			$('#container4').attr('class','show')
		}
	}
	</script>
</head>
<body>
<div id="wrap2">
<div class="title">월별 다이어트 추이
</div>
<div class="nav" style="padding:0 0 0 200px">
	<!-- 이전해 -->
	<a class="before_after_year" href="./chart.com?yy=${param.yy-1}&mon=${param.mon}">
		&lt;&lt;
	</a> 
	<!-- 이전달 -->
	<a class="before_after_month" href="./chart.com?yy=${param.yy}&mon=${param.mon-1}">
		&lt;
	</a> 
	<!-- 이번달 -->
	<span class="this_month">
		&nbsp;${param.yy}. 
		<c:if test="${param.mon<10}">0</c:if>${param.mon}&nbsp;
	</span>
	<!-- 다음달 -->
	<a class="before_after_month" id="nextmon" onclick="nextmonth(${param.yy},${param.mon})">
		&gt;
	</a> 
	<!-- 다음해 -->
	<a class="before_after_year" id="nextyy" onclick="nextyear(${param.yy},${param.mon})">
		&gt;&gt;
	</a>
	<input type="button" value="선택" onclick="change()" class="btn2" style="float:right;margin:13px 10px 0;"/>
	<select id="type" class="btn" style="width:110px;float:right;margin:13px 0 0;padding:0">
		<option value="w">몸무게</option>
		<option value="f">음식 칼로리</option>
		<option value="e">운동 칼로리</option>
		<option value="s">월별 성공률</option>
	</select>
</div>
<figure class="highcharts-figure">
    <div id="container1" class="show"></div>
    <div id="container2" class="hidden"></div>
    <div id="container3" class="hidden"></div>
    <div id="container4" class="hidden"></div>
</figure>
</div>

<script>
Highcharts.chart('container1', {
	  chart: {
	    type: 'line'
	  },
	  title: {
	    text: ''
	  },
	  xAxis: {
	    categories: [<c:forEach var="list" items="${LIST}"  >${list.day},</c:forEach>]
	  },
	  yAxis: {
	    title: {
	      text: 'Weight (Kg)'
	    }
	  },
	  plotOptions: {
	    line: {
	      dataLabels: {
	        enabled: true
	      },
	      enableMouseTracking: false
	    }
	  },
	  series: [{
		name: '몸무게',
		color:'#FF7289',
	    data: [<c:forEach var="list" items="${LIST}"  ><c:if test="${list.dweight != 0}">${list.dweight},</c:if></c:forEach>]
	  }]
	});
	
Highcharts.chart('container2', {
	  chart: {
	    type: 'line'
	  },
	  title: {
	    text: ''
	  },
	  xAxis: {
	    categories: [<c:forEach var="list" items="${LIST}"  >${list.day},</c:forEach>]
	  },
	  yAxis: {
	    title: {
	      text: 'Food Calorie'
	    }
	  },
	  plotOptions: {
	    line: {
	      dataLabels: {
	        enabled: true
	      },
	      enableMouseTracking: false
	    }
	  },
	  series: [{
		name: '음식 칼로리',
		color:'#FF7289',
	    data: [<c:forEach var="list" items="${LIST}"  ><c:if test="${list.dfoodcal != 0}">${list.dfoodcal},</c:if></c:forEach>]
	  }]
	});
	
Highcharts.chart('container3', {
	  chart: {
	    type: 'line'
	  },
	  title: {
	    text: ''
	  },
	  xAxis: {
	    categories: [<c:forEach var="list" items="${LIST}"  >${list.day},</c:forEach>]
	  },
	  yAxis: {
	    title: {
	      text: 'Exercise Calorie'
	    }
	  },
	  plotOptions: {
	    line: {
	      dataLabels: {
	        enabled: true
	      },
	      enableMouseTracking: false
	    }
	  },
	  series: [{
	    name: '운동 칼로리',
	    color:'#FF7289',
	    data: [<c:forEach var="list" items="${LIST}"><c:if test="${list.dexercal != 0}">${list.dexercal},</c:if></c:forEach>]
	  }]
	});

Highcharts.chart('container4', {
	  chart: {
	    zoomType: 'xy'
	  },
	  title: {
	    text: '월별 성공률'
	  },
	  xAxis: [{
	    categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
	      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	    crosshair: true
	  }],
	  yAxis: [{
	    title: {
	      text: 'Success Rate',
	      style: {
	        color: Highcharts.getOptions().colors[1]
	      }
	    }
	  }],
	  tooltip: {
	    shared: true
	  },
	  series: [{
	    name: 'Success Rate',
	    type: 'column',
	    color:'#FF7289',
	    yAxis: 0,
	    data: [<c:forEach var="rate" items="${RATE}">${rate.value},</c:forEach>],
	    tooltip: {
	      valueSuffix: ' %'
	    }
	  }]
	});
</script>

</body>
</html>