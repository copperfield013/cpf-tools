<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
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
			th, td,th input[type="text"],td input[type="text"]{
				text-align: center;
			}
			th input[type="text"],td input[type="text"]{
				
			}
		</style>
	</head>
	<body>
		<div id="page">
			<div class="commend">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<div class="col-lg-6 form-inline">
							<input id="add-row" class="btn btn-primary" type="button" value="添加行" />
							<input id="add-col" class="btn btn-primary" type="button" value="添加列" />
						</div>
					</div>
				</form>
			</div>
			<table class="table table-bordered">
				<thead id="main-thead">
					<tr>
						<th><input type="text" class="form-control" value="序号"/></th>
					</tr>
				</thead>
				<tbody id="main-tbody">
					<tr>
						<td><input type="text" class="form-control" /></td>
					</tr>
				</tbody>
				<tfoot>
				</tfoot>
			</table>
		</div>
		
		<script type="text/javascript">
			$(function(){
				
				var $thead = $('#mian-thead');
				var $tbody = $('#main-tbody');
				var $rowTemp = $tbody.children('tr').first().clone();
				
				function addRow(index){
					var $trs = $tbody.children('tr');
					if(index === undefined || $trs.length == 0 || index > $trs.length){
						$tbody.append($rowTemp.clone());
					}else{
						$trs.eq(index).before($rowTemp.clone());
					}
				}
				function addCol(index){
					
				}
				
				$('#add-row').click(function(){
					$('#main-tbody').append($rowTemp.clone());
				});
				$('#add-col').click(function(){
					
				});
				
				
			});
		</script>
	</body>
</html>