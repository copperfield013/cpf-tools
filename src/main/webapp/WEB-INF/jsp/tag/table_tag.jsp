<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		
		
		<title>from-to</title>
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link href="media/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="media/js/jquery-3.2.1.js"></script>
		<script type="text/javascript" src="media/js/jquery.tmpl.js"></script>
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="media/js/bootstrap.min.js"></script>
		<script src="media/js/jquery.tmpl.js"></script>
		<script type="text/javascript" src="media/js/jquery.PrintArea.js"></script>
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
			a.del{
				cursor: pointer;
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
			table{
				width: 100%;
			}
			table,tr,td{
				border: 1px solid #000;
				text-align: center;
			}
		</style>
	</head>
	<body>
		<div id="page">
			<h2>60×40</h2>
			<div id="container">
				<div id="print-area">
					<table>
						<tbody>
							<!-- <tr>
								<td>冰箱</td>
								<td>厨房</td>
							</tr>
							<tr>
								<td>空调外机</td>
								<td>卫生间</td>
							</tr>
							<tr>
								<td>客厅内机</td>
								<td>太阳能</td>
							</tr>
							<tr>
								<td>主卧内机</td>
								<td>洗衣机</td>
							</tr> -->
							<tr>
								<td>次卧内机</td>
								<td>普杆</td>
							</tr>
							<tr>
								<td>书房空调</td>
								<td>普杆</td>
							</tr>
							<tr>
								<td>总开漏保</td>
								<td>照明</td>
							</tr>
							<tr>
								<td>照明</td>
								<td>照明</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<button id="print" href="#" class="btn btn-primary">打印</button>
		</div>
		
	
		<script type="text/javascript">
			$(function(){
				$('#print').click(function(){
					
					$('#print-area').printArea({ 
			    		mode 		: "iframe", 
			    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
			    		extraCss	: $('base').attr('href') + 'media/css/table-tag.css',
			    		posWidth	: '60mm',
			    		posHeight	: '40mm'
			    	})
				});
			});
		</script>
	</body>
</html>