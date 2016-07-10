<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>economy</title>
<link rel="stylesheet"
	href='<c:url value="/javascript/bootstrap-3.3.2-dist/css/bootstrap.min.css"></c:url>'>
<link rel="stylesheet"
	href='<c:url value="/javascript/bootstrap-3.3.2-dist/css/bootstrap-theme.min.css"></c:url>'>
<link rel="stylesheet"
	href='<c:url value="/javascript/Font-Awesome-master/css/font-awesome.min.css"></c:url>'>
<script
	src='<c:url value="/javascript/jquery-2.1.4/jquery-2.1.4.min.js"></c:url>'></script>
<script
	src='<c:url value="/javascript/bootstrap-3.3.2-dist/js/bootstrap.min.js"></c:url>'></script>
</head>
<body>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<button type="button" class="btn btn-success" tabindex="-1">經濟數據</button>
			 : 
			<input type="radio" name="radio" value="gdp" checked="checked"> <span class="label label-warning">GDP</span>
			<input type="radio" name="radio" value="exch"> <span class="label label-warning">Exchange Rate</span>
		</div>
		<div class="panel-body danger">
			<span class="text-danger">說明 ： 取得經濟數據資料</span>
		</div>
		<div id="main" style="height: 400px"></div>
	</div>
</body>
<script
	src='<c:url value="/javascript/echarts-2.2.4/build/dist/echarts.js"></c:url>'></script>
<script type="text/javascript">
	require
			.config({
				paths : {
					echarts : '<c:url value="/javascript/echarts-2.2.4/build/dist"></c:url>'
				}
			});
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		var myChart = ec.init(document.getElementById('main'));
		$(function() {
			var type = "gdp";
			$.ajax({
				url : '<c:url value="/demo/economy/data"></c:url>',
				method : 'GET',
				contentType : 'application/json; charset=UTF-8',
				data : {
					"type" : type
				}
			}).done(function(data, textStatus, jqXHR) {
				legend = data["legends"];
				xAxis = data["xAxises"];
				series = [];
				
				console.log(legend);
				
				for (i = 0, size = legend.length - 1; i < size; ++i) {
					obj =  {
						"name" : legend[i],
						"type" : 'bar',
						"data" : data["data"][i]
					}
					series.push(obj);
				}
				myChart.setOption(genOption(legend, xAxis, series));
			});
			function genOption(legend, xAxis, series) {
				return {
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : legend
					},
					xAxis : [ {
						type : 'category',
						data : xAxis
					} ],
					yAxis : [ {
						type : 'value'
					} ],
					series : series
				};
			}
		});
	});
</script>
</html>