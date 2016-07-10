<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>facebook feed</title>
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
			<tbody id="tbody">
			</tbody>
		</table>
		<div class="panel-footer">
			<a id="more" href="javascript:void(0)" class="btn btn-info" role="button">more</a>
		</div>
	</div>
</body>
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
		$("#more").on("click", function() {
			start  = start + 15;
			$.ajax({
				url : $("#more").attr("href"),
				method : 'GET',
				contentType : 'application/json; charset=UTF-8'
			}).done(function(news, textStatus, jqXHR) {
				appendNews(news);
			});
			return false;
		});
		function clearNews() {
			var table = $("#tbody")
			table.empty();
		}
		function appendNews(news) {
			var table = $("#tbody")
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
			$("#more").attr("href", '<c:url value="/demo/news/data"></c:url>?term=' + term + '&start=' + (start + 15));
		}
	});
</script>
</html>