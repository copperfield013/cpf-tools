<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
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
			<form action="demo/checkHasHouseholder.html" method="post">
				<label>文件路径</label>
				<textarea rows="20" cols="30" name="source"></textarea>
				<input type="submit" value="提交" >
			</form>
		</div>
		<div id="right">
			<label>生成bat</label>
			<pre ></pre>
		</div>
	</body>
</html>