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
			<input type="radio" name="radio" value="exchange_rate"> <span class="label label-warning">Exchange Rate (歐元對其他貨幣)</span>
		</div>
		<div class="panel-body danger">
			<span class="text-danger">說明 ： 取得經濟數據資料</span>
		</div>
		<div id="main" style="height: 400px"></div>
	</div>
	<div class="col-lg-6 col-md-6 col-sm-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="input-group">
					<div class="input-group-btn">
						<button type="button" class="btn btn-success" tabindex="-1">關鍵字</button>
					</div>
					<input id="keyword" type="text" class="form-control" value="brexit">
					<div class="input-group-btn">
						<button id="search" type="button" class="btn btn-warning" tabindex="-1">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</div>
			<div class="panel-body danger">
				<span class="text-danger">說明 ： 取得包含某特定關鍵字新聞</span>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr class="info">
						<th>新聞來源</th>
						<th>新聞標題</th>
					</tr>
				</thead>
				<tbody id="newsTbody">
				</tbody>
			</table>
			<div class="panel-footer">
				<a id="newsMore" href="javascript:void(0)" class="btn btn-info" role="button">more</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function() {
				var term = "brexit";
				var start = 0;
				$.ajax({
					url : '<c:url value="/demo/news/data"></c:url>',
					method : 'GET',
					contentType : 'application/json; charset=UTF-8',
					data : {
						"term" : term,
						"start" : start
					}
				}).done(function(news, textStatus, jqXHR) {
					appendNews(news);
				});
				$("#search").on("click", function() {
					term = $("#keyword").val();
					start = 0;
					$.ajax({
						url : '<c:url value="/demo/news/data"></c:url>',
						method : 'GET',
						contentType : 'application/json; charset=UTF-8',
						data : {
							"term" : term,
							"start" : start
						}
					}).done(function(news, textStatus, jqXHR) {
						clearNews();
						appendNews(news);
					});
				});
				$("#newsMore").on("click", function() {
					start  = start + 15;
					$.ajax({
						url : $("#newsMore").attr("href"),
						method : 'GET',
						contentType : 'application/json; charset=UTF-8'
					}).done(function(news, textStatus, jqXHR) {
						appendNews(news);
					});
					return false;
				});
				function clearNews() {
					var table = $("#newsTbody")
					table.empty();
				}
				function appendNews(news) {
					var table = $("#newsTbody")
					var tr;
					var td;
					var a;
					var id;
					var source;
					var title;
					var url;
					for (i = 0, size = news.length; i < size; ++i) {
						tr = $("<tr>");
						source = news[i]["source"];
						title = news[i]["title"];
						url = news[i]["url"];
						td = $("<td width='100px'>");
						a = $("<a href='" + url + "' target='_blank'>");
						a.text(source);
						td.append(a);
						tr.append(td);
						td = $("<td>");
						a = $("<a href='" + url + "' target='_blank'>");
						a.text(title);
						td.append(a);
						tr.append(td);
						table.append(tr);
					}
					$("#newsMore").attr("href", '<c:url value="/demo/news/data"></c:url>?term=' + term + '&start=' + (start + 15));
				}
			});
		</script>
	</div>
	<div class="col-lg-6 col-md-6 col-sm-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<button type="button" class="btn btn-success" tabindex="-1">政要</button>
				 : 
				<div class="btn-group">
					<div class="btn-group">
						<button type="button" class="btn btn-warning dropdown-toggle"
							data-toggle="dropdown">
							<span id="fb_name">Barack Obama</span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="javascript:void(0);" class="btn fbid" data-page-id="6815841748">Barack Obama</a></li>
							<li><a href="javascript:void(0);" class="btn fbid" data-page-id="653092548048400">David Cameron</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="panel-body danger">
				<span class="text-danger">說明 ： 取得某位政要的即時臉書貼文</span>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr class="info">
						<th>貼文時間</th>
						<th>貼文內容</th>
					</tr>
				</thead>
				<tbody id="fbTbody">
				</tbody>
			</table>
			<div class="panel-footer">
				<a id="fbMore" href="javascript:void(0)" class="btn btn-info" role="button">more</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function() {
				var pageId = "6815841748";
				$.ajax({
					url : '<c:url value="/demo/facebook/feed"></c:url>',
					method : 'GET',
					contentType : 'application/json; charset=UTF-8',
					data : {
						"pageId" : pageId
					}
				}).done(function(feeds, textStatus, jqXHR) {
					appendFeeds(feeds);
				});
				$(".fbid").on("click", function() {
					$this = $(this);
					var pageId = $this.data("pageId");
					$("#fb_name").text($this.text());
					$.ajax({
						url : '<c:url value="/demo/facebook/feed"></c:url>',
						method : 'GET',
						contentType : 'application/json; charset=UTF-8',
						data : {
							"pageId" : pageId
						}
					}).done(function(feeds, textStatus, jqXHR) {
						clearFeeds();
						appendFeeds(feeds);
					});
				});
				$("#fbMore").on("click", function() {
					$.ajax({
						url : $("#fbMore").attr("href"),
						method : 'GET',
						contentType : 'application/json; charset=UTF-8'
					}).done(function(feeds, textStatus, jqXHR) {
						appendFeeds(feeds);
					});
					return false;
				});
				function clearFeeds() {
					var table = $("#fbTbody")
					table.empty();
				}
				function appendFeeds(feeds) {
					var table = $("#fbTbody")
					var tr;
					var td;
					var a;
					var id;
					var message;
					var created_time;
					var dt;
					var month;
					var day;
					var hour;
					var minute;
					var data = feeds["data"];
					for (i = 0, size = data.length; i < size; ++i) {
						tr = $("<tr>");
						id = data[i]["id"];
						message = data[i]["message"];
						created_time = data[i]["created_time"];
						td = $("<td width='150px'>");
						a = $("<a href='https://www.facebook.com/" + id + "' target='_blank'>");
						dt = new Date(created_time);
						a.text(dt.getFullYear() + "/" + appendZero(dt.getMonth()+1) + "/" + appendZero(dt.getDate()) + " " + appendZero(dt.getHours()) + ":" + appendZero(dt.getMinutes()));
						td.append(a);
						tr.append(td);
						td = $("<td>");
						a = $("<a href='https://www.facebook.com/" + id + "' target='_blank'>");
						a.text(message);
						td.append(a);
						tr.append(td);
		
						table.append(tr);
					}
					var paging = feeds["paging"];
					$("#fbMore").attr("href", paging["next"]);
				}
				function appendZero(num) {
					return num >= 10 ? num : ("0" + num);
				}
			});
		</script>
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
			render(type);
			$('input[name="radio"]:radio' ).on("change", function() {
				$this = $(this);
				type = $this.val();
				render(type);
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
			function render(type) {
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
			}
		});
	});
</script>
</html>