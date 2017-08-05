<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>条线标签纸打印</title>
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
		<!-- 可选的Bootstrap主题文件（一般不使用） -->
		<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
		<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
		<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<style type="text/css">
			body{
				position: absolute;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				font-size: 18px;
				font-family: 微软雅黑;
			}
			#page{
				position: relative;
				left: 20%;
				top: 20px;
				width: 40em;
				border: 1px solid;
				padding: 10px 20px;
			}
			.container{
				width: 100% !important;
			}
		</style>
	</head>
	<body>
		<div id="page">
			<div class="container">
				<form class="form-horizontal" role="form" method="post" action="pimport/doReplaceTypeInt">
					<div class="form-group">
						<label class="col-lg-2">数据</label>
						<div class="col-lg-8 inline">
							<textarea name="data" class="form-control" rows="8">${table }</textarea>
						</div>
					</div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				
			});
			
			
		</script>
	</body>
</html>