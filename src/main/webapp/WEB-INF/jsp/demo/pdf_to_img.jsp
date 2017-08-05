<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>PDF转图片</title>
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		<style type="text/css">
			
		</style>
	</head>
	<body>
		<div id="left">
			<form action="demo/converter_pdf_to_img.html" method="post" enctype="multipart/form-data">
				<label>数据文件</label>
				<input name="file" type="file" >
				分辨率：<input name="dpi" type="text" value="500">
				<input type="submit" value="转换" >
			</form>
			<div>
				转换后的图片都放在了zip压缩包文件里面<br/>
				分辨率一般来说500就蛮高的了，
				分辨率越高，转换时间越长，图片文件越大。<br/>
				如果要转的PDF的页数非常多（十几张以上），那么转换时间可能会很长，
				后续有时间的话我会做个进度条<br/>
			</div>
		</div>
	</body>
</html>