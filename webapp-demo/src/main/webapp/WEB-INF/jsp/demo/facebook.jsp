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
			即時臉書貼文 : 
			<div class="btn-group btn-group-xs">
				<div class="btn-group btn-group-xs">
					<button type="button" class="btn btn-primary dropdown-toggle"
						data-toggle="dropdown">
						<span id="fb_name">Barack Obama</span> <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#" class="btn btn-xs fbid" data-page-id="6815841748">Barack Obama</a></li>
						<li><a href="#" class="btn btn-xs fbid" data-page-id="653092548048400">David Cameron</a></li>
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
		$("#more").on("click", function() {
			$.ajax({
				url : $("#more").attr("href"),
				method : 'GET',
				contentType : 'application/json; charset=UTF-8'
			}).done(function(feeds, textStatus, jqXHR) {
				appendFeeds(feeds);
			});
			return false;
		});
		function clearFeeds() {
			var table = $("#tbody")
			table.empty();
		}
		function appendFeeds(feeds) {
			var table = $("#tbody")
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
			$("#more").attr("href", paging["next"]);
		}
		function appendZero(num) {
			return num >= 10 ? num : ("0" + num);
		}
	});
</script>
</html>