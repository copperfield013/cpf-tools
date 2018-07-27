<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		
		
		<title>材料清单标签纸打印</title>
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
			<h2>70×50</h2>
			<div class="container">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-lg-2">内容</label>
						<div class="col-lg-8">
							<textarea id="titles" class="form-control" rows="10" placeholder="编码\t标题\tAF:AF数|RE:RE数"></textarea>
							<span class="help-span">数目支持格式：1、1,2,3、1~10</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2">数量</label>
						<div class="col-lg-8">
							<input id="count" type="number" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-3 col-lg-4">
							<input id="add" class="form-control" type="button" value="添加" />
						</div>
					</div>
				</form>
				<table class="table">
					<thead>
						<tr>
							<th>序号</th>
							<th>编号</th>
							<th>标题</th>
							<th>材料列表</th>
							<th>数量</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="mainData">
					</tbody>
				</table>
				<div class="row">
					<div class="col-lg-offset-3 col-lg-6">
						<input id="print" type="button" class="btn btn-primary btn-lg" value="打印" />
					</div>
				</div>
			</div>
		</div>
		
		
		
		<script type="text/x-jquery-tmpl" id="mainTableRow">
			<tr>
				<td class="rowNo">\${rowNo}</td>
				<td class="infoNo">\${infoNo}</td>
				<td class="title">\${title}</td>
				<td class="refer">\${refer}</td>
				<td class="count">\${count}</td>
				<td>
					<a class="del">删除</a>
				</td>
			</tr>
		</script>
		
		<script type="text/x-jquery-tmpl" id="print-table">
			<div class="wrapper">
				<div class="table-page">
				<table>
					<thead>
						<tr>
							<th id="title" colspan="4">\${infoNo}\${title}</th>
						</tr>
						<tr>
							<th width="25%">编号</th>
							<th width="25%">数量</th>
							<th width="25%">编号</th>
							<th width="25%">数量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>\${tit_0}</td>
							<td></td>
							<td>\${tit_6}</td>
							<td></td>
						</tr>
						<tr>
							<td>\${tit_1}</td>
							<td></td>
							<td>\${tit_7}</td>
							<td></td>
						</tr>
						<tr>
							<td>\${tit_2}</td>
							<td></td>
							<td>\${tit_8}</td>
							<td></td>
						</tr>
						<tr>
							<td>\${tit_3}</td>
							<td></td>
							<td>\${tit_9}</td>
							<td></td>
						</tr>
						<tr>
							<td>\${tit_4}</td>
							<td></td>
							<td>\${tit_10}</td>
							<td></td>
						</tr>
						<tr>
							<td>\${tit_5}</td>
							<td></td>
							<td>\${tit_11}</td>
							<td></td>
						</tr>
					</tbody>
				</table>
				</div>
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
					var titleStr = $('#titles').val();
					var titles = titleStr.split('\n');
					var lastNo = 0;
					var lastNoStr = $('#mainData').find('tr:last').children('td.rowNo').text();
					if(lastNoStr){
						lastNo = parseInt(lastNoStr);
					}
					var $mainTableRow = $('#mainTableRow');
					for(var i in titles){
						var title = titles[i];
						if(title != ''){
							var split = title.split('\t');
							var infoNo = split[0],
								title = split[1],
								refer = split[2];
							infoNo.replace(' ', '&nbsp;');
							var $row = $mainTableRow.tmpl({
								rowNo	: lastNo + parseInt(i) + 1,
								infoNo	: infoNo,
								title	: title,
								count	: count,
								refer	: refer
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
						var infoNo = $(this).find('.infoNo').text(),
							title = $(this).find('.title').text(),
							count = parseInt($(this).find('.count').text()),
							refer = $(this).find('.refer').text();
						var referMap = toReferMap(refer, infoNo);
						
						var $table = $printTable.tmpl($.extend({
							infoNo	: infoNo,
							title	: title
						}, referMap));
						while(count-->0){
							$printPage.append($table.clone());
						}
					});
					$printPage.printArea({ 
			    		mode 		: "iframe", 
			    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
			    		extraCss	: $('base').attr('href') + 'media/css/refer-tag-print.css',
			    		posWidth	: '70mm',
			    		posHeight	: '50mm'
			    	})
				});
				
				function toReferMap(referStr, infoNo){
					var map = {}, index = 0;
					if(referStr){
						var refers = referStr.split('|');
						for(var i in refers){
							var split = refers[i].split(':');
							var prefix = split[0],
								list = split[1];
							var items = list.split(',');
							var arr = [], referList = [];
							for(var j in items){
								arr = arr.concat(convert(items[j]));
							}
							for(var j =0; j < 12; j++){
								if(arr.indexOf(j) >= 0){
									map['tit_' + index++] = prefix + infoNo + '-' + j;
								}
							}
						}
					}
					while(index < 12){
						map['tit_' + index++] = '&nbsp;';
					}
					return map;
				}
				
				function convert(str){
					var range = [];
					var split = str.split('~');
					if(split.length == 1){
						var num = parseInt(split[0]);
						if(num>=0){
							range.push(num);
						}
					}else if(split.length == 2){
						var start = parseInt(split[0]),
							end = parseInt(split[1]);
						if(isNaN(start)){
							start = 1;
						}
						if(end >= start){
							for(var i = start; i <= end; i++){
								range.push(i);
							}
						}
					}
					return range;
				}
				
				function refreshRowNo(){
					$('#mainData').find('tbody tr').each(function(i){
						$(this).find('td.rowNo').text(i + 1);
					});
				}
				
			});
		</script>
	</body>
</html>