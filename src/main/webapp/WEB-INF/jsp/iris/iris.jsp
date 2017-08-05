<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		
		<script src="media/plugins/iris/json2.js"></script>
		<script src="media/plugins/iris/jquery-1.9.1.min.js"></script>
		<script src="media/plugins/iris/Iris.js"></script>
		<style type="text/css">
			.contentvideo {
				text-align: center;
				width: 570;
				height: 412x;
				border: solid 4px;
				margin: 0 auto;
				border-color: black;
				background-color: whilesmoke;
				padding: 10px;
			}
		</style>
	</head>
	<body>
		<div id="contentvideoJS" class="contentvideo">
		</div>
		<div>
			<input type="button" value="注册" id="enrollButton" />
			<input type="text" id="uuid" />
			<input type="button" value="识别" id="matchButton" />
			<input type="button" value="停止" id="stopButton" />
		</div>
		<script type="text/javascript">
			$(function(){
				var iris = new Iris();
				
				iris.appendTo($('#contentvideoJS'));
				
				$('#enrollButton').click(function(){
					iris.startEnroll(function(uuid){
						$('#uuid').val(uuid);
						alert('注册成功' + uuid);
					});
				});
				
				$('#matchButton').click(function(){
					iris.startMatch($('#uuid').val(), function(matched, error){
						if(matched){
							alert('匹配成功');
						}
					});
				});
				
				$('#stopButton').click(function(){
					iris.stop();
				});
			});
		</script>
	</body>
</html>