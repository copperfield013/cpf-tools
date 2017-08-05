<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>工具箱-By张荣波</title>
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		<link rel="stylesheet" type="text/css"  href="media/css/index.css" />
		<script type="text/javascript" src="media/js/jquery-1.9.0.js"></script>
		<script type="text/javascript" src="media/js/index.js"></script>
	</head>
	<body>
		<div class="list">
			<dl>
				<dt>人口导入工具</dt>
				<dd>
					<input type="button" value="随机名称生成" onclick="location.href='demo/excel_rename.html'" />
					<input type="button" value="Excel判断户主" onclick="location.href='demo/goCheckHasHouseholder.html'" />
					<input type="button" value="Excel导出楼栋" onclick="location.href='demo/export_building.html'" />
					<input type="button" value="Excel导出重复" onclick="location.href='demo/export_repeat.html'" />
					<input type="button" value="Excel覆盖类型" onclick="location.href='pimport/replace_typeint'" />
				</dd>
			</dl>
			<dl>
				<dt>文件转换工具</dt>
				<dd>
					<input type="button" value="PDF转图片" onclick="location.href='demo/pdf_to_img.html'" />
				</dd>
			</dl>
			<dl>
				<dt>西湖的水</dt>
				<dd>
					<input type="button" value="设备标签" onclick="location.href='demo/print_tag.html'" />
					<input type="button" value="批量设备标签" onclick="location.href='tag/device.html'" />
					<input type="button" value="条线标签" onclick="location.href='extTag/main.html'" />
				</dd>
			</dl>
		</div>
	</body>
</html>