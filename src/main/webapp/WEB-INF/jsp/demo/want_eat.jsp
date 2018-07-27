<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<title>我的内心毫无波澜，甚至还有点想吃……</title>
		<!-- meta使用viewport以确保页面可自由缩放 -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- 引入 jQuery Mobile 样式 -->
		<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
		
		<!-- 引入 jQuery 库 -->
		<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
		
		<!-- 引入 jQuery Mobile 库 -->
		<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	</head>
	
	<body>
		<div data-role="page">
			<div data-role="header">
				<h1>我的内心毫无波澜，甚至还有点想吃……</h1>
			</div>
	
			<div data-role="content">
				<form method="get" action="${basePath }demo/eat">
					<label for="foods">想吃什么，换行分隔</label> 
					<textarea rows="10" name="foods"></textarea>
					<input type="submit" value="生成"> 
				</form>
			</div>
		</div>
	</body>
</html>