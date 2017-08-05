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
		</style>
	</head>
	<body>
		<div id="page">
			<div class="container">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-lg-2">内容</label>
						<div class="col-lg-8">
							<textarea id="content" class="form-control" rows="10"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2">数量</label>
						<div class="col-lg-8">
							<input id="count" type="number" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-3 col-lg-6 form-inline">
							<input id="add" class="form-control inline" type="button" value="添加" />
						</div>
					</div>
				</form>
				<table class="table">
					<thead>
						<tr>
							<th>序号</th>
							<th>条线</th>
							<th>姓名</th>
							<th>地址</th>
							<th>电话</th>
							<th>数量</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="mainData">
					</tbody>
				</table>
				<div class="row">
					<div class="col-lg-offset-3 col-lg-6">
						<input id="submit" type="button" class="btn btn-primary btn-lg" value="生成标签预览" />
						<input id="print" type="button" disabled="disabled" class="btn btn-primary btn-lg" value="打印" />
					</div>
				</div>
			</div>
		</div>
				
		<script type="text/x-jquery-tmpl" id="tag-page">
			<div id="container">
				<table >
					<tbody>
						<tr>
							<th>\${rowNo}</th>
							<th>条线</th>
							<th>姓名</th>
							<th>地址</th>
							<th>电话</th>
							<th>数量</th>
							<th>操作</th>
						</tr>
					</tbody>
				</table>
			</div>
		</script>
		<script type="text/javascript">
			$(function(){
				$('#add').click(function(){
					var count = $('#count').val();
					if(!count){
						alert('请输入有效的数量');
						return false;
					}
					var content = $('#content').val();
					var contents = content.split('\n');
					var lastNo = 0;
					var lastNoStr = $('#mainData').find('tr:last').children('td.rowNo').text();
					if(lastNoStr){
						lastNo = parseInt(lastNoStr);
					}
					var $mainTableRow = $('#mainTableRow');
					for(var i in contents){
						var title = contents[i];
						if(title != ''){
							var split = title.split('\t');
							var sender = split[0],
								receiver = split[1];
							var $row = $mainTableRow.tmpl({
								rowNo	: lastNo + parseInt(i) + 1,
								sender	: sender,
								receiver: receiver,
								count	: count
							});
							$('#mainData').append($row);
						}
					}
				});
				$(document).on('click', '.del', function(){
					$(this).closest('tr').remove();
					refreshRowNo();
					return false;
				});
				
				
				$('#print').click(function(){
					var $printPage = $('<div>');
					var $printTable = $('#print-table');
					$('#mainData tr').each(function(){
						var sender = $(this).find('.sender').text(),
							receiver = $(this).find('.receiver').text(),
							count = parseInt($(this).find('.count').text());
							;
						
						var $table = $printTable.tmpl({
							sender	: sender,
							receiver	: receiver
						});
						while(count-->0){
							$printPage.append($table.clone());
						}
					});
					$printPage.printArea({ 
			    		mode 		: "iframe", 
			    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
			    		extraCss	: $('base').attr('href') + 'media/css/event-tag-print.css',
			    		posWidth	: '70mm',
			    		posHeight	: '50mm'
			    	})
				});
				
				function refreshRowNo(){
					$('#mainData').find('tbody tr').each(function(i){
						$(this).find('td.rowNo').text(i + 1);
					});
				}
			});
			
		</script>
	</body>
</html>