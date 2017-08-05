<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>楼栋导出</title>
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
			<form action="demo/generateBuilding.html" method="post" enctype="multipart/form-data">
				<label>数据文件</label>
				<input name="file" type="file" >
				<input name="sheetName" type="text" value="Sheet1">
				<input type="submit" value="提交" >
			</form>
			<ul>
				<li>导出楼栋数据</li>
				<li>表头在第1行</li>
				<li>数据从第2行开始</li>
				<li>楼栋名在B列</li>
				<li>单元名在C列</li>
				<li>房间号在D列</li>
			</ul>
		</div>
	</body>
</html>