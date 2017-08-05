<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>重复人口导出</title>
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		<style type="text/css">
			div {
			    display: block;
			    float: left;
			}
			* {
			    font-family: 微软雅黑;
			}
			body>div {
			    width: 45%;
			    margin: 1%;
			    border: 2px dotted;
			    padding: 1%;
			    height: 500px;
			}
		</style>
	</head>
	<body>
		<div id="left">
			<form action="demo/generateRepeat.html" method="post" enctype="multipart/form-data">
				<label>数据文件</label>
				<input name="file" type="file" >
				<input name="sheetName" type="text" value="Sheet1">
				<input type="submit" value="提交" >
			</form>
			<div>
				<ul>
					<li>判断人口是否重复</li>
					<li>表头在第1行</li>
					<li>数据从第2行开始</li>
					<li>身份证号是F列</li>
				</ul>
			</div>
		</div>
	</body>
</html>