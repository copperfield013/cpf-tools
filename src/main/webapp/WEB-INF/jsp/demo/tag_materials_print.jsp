<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>设备标签纸打印</title>
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		<link rel="stylesheet" href="media/plugins/jquery-ui-1.12.1.custom/jquery-ui.css">
		<script type="text/javascript" src="media/js/jquery-1.9.0.js"></script>
		<script type="text/javascript" src="media/js/jquery.PrintArea.js?1111"></script>
		<script src="media/plugins/jquery-ui-1.12.1.custom/jquery-ui.js"></script>
		<script src="media/js/jquery.ui.datepicker-zh-CN.js"></script>
		<script src="media/plugins/fileUpload/jquery.fileupload.js"></script>
		
		<style type="text/css">
			body{
				position: relative;
			}
			#full{
				position: absolute;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
			}
			table, thead, tbody, tr, td, th{
				border: 1px solid #000;
			    border-collapse: collapse;
			    padding: 0.2em 1em;
			    text-align: center;
			    white-space: nowrap;
			}
			table{
				margin: 0 auto;
			}
			#container th{
				font-size: 2em;
			}
			#container input[type="text"] {
			    font-size: 1.5em;
			    border: none;
			    text-align: center;
			    width: 100%;
			}
			#print {
			    display: inline-block;
			    background-color: #cd0978;
			    width: 6em;
			    text-align: center;
			    font-style: initial;
			    height: 2em;
			    line-height: 2em;
			    color: #ddd;
			    font-weight: bold;
			    cursor: pointer;
			}
			#print:HOVER {
				background-color: 00cd89;
			}
			#print:FOCUS {
				background-color: 98cd89
			}
			.font-resize{
				font-size: 2em;
				cursor: pointer;
				margin: 5px;
				font-style: none;
			}
			.font-resize:HOVER {
				background-color: #ccc;
			}
			tfoot>tr>td>.font-resize:last-child{
				margin-right: 1em;	
			}
			*[font-level="1"]{
				font-size: 1.2em !important;
			}
			*[font-level="2"]{
				font-size: 1em !important;
			}
			*[font-level="3"]{
				font-size: 0.8em !important;
			}
			#storage-way img{
				width: 1em;
			    height: 1em;
			    vertical-align: middle;
			    padding: 0;
			}
		</style>
	</head>
	<body>
		<div id="full">
			<div id="container">
				<table>
					<tbody>
						<tr>
							<td class="title">品&nbsp;&nbsp;名</td>
							<td class="data" colspan="3">
							</td>
						</tr>
						<tr id="storage-way">
							<td class="title">储存方式</td>
							<td class="data" >
								<img src="${basePath }/media/image/square_stroke.svg">
								<span>常温</span>
							</td>
							<td class="data">
								<img src="${basePath }/media/image/square_stroke.svg">
								<span>冷藏</span>
							</td>
							<td class="data">
								<img src="${basePath }/media/image/square_stroke.svg">
								<span>冷冻</span>
							</td>
						</tr>
						<tr>
							<td class="title">保质期</td>
							<td class="data" colspan="3">
								<span class="expiration-day">天</span>
							</td>
						</tr>
						<tr>
							<td class="title">有效期</td>
							<td class="data" colspan="3" id="date">
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<i class="font-resize add">+</i>
								<i class="font-resize sub">-</i>
								<i id="print">打印</i>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				$('#date').datepicker({
					'dateFormat':	'yy年mm月dd日'
				});
				var currText = null;
				
				$(':text').focus(function(){
					currText = this;
				});
				$('i.add,i.sub').click(function(){
					if(currText){
						var fontLevel = $(currText).attr('font-level');
						var add = $(this).is('.add')? -1: 1;
						if(fontLevel){
							var toLevel = Number(fontLevel) + add;
							if(toLevel <= 3){
								$(currText).attr('font-level', toLevel);
							}
						}else if(add === 1){
							$(currText).attr('font-level', 1);
						}
					}
				});
				$('#print').click(function(){
					if(confirm('确认打印？')){
						var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>';
						var options = { 
				    		mode 		: "iframe", 
				    		extraHead 	: headElements,
				    		posWidth	: '60mm',
				    		posHeight	: '40mm',
				    		deferMill	: 2000,
				    		extraCss	: $('base').attr('href') + 'media/css/printMaterial.css'
				    	};
						var $container = $('#container').clone();
						$(':text', $container).replaceWith(function(){
							return $('<label>').text($(this).val()).attr('font-level', $(this).attr('font-level'));
						}).each(function(){
							console.log($(this).width());
						});
						$container.find('tfoot').remove();
						$container.printArea(options);
					}
				});
				var data = null;
				$('#file').fileupload({
			        dataType : 'json',
			        url		 : 'demo/print_with_excel',
			        add	: function(e, _data){
			        	data = _data;
			        	console.log(data);
			        },
			        done: function (e, data) {
			        	console.log('done');
			        	console.log(data);
			        }
			    });
				$('#upload').click(function(){
					if(data){
						data.submit().done(function(res){
							
						});
					}
				});
			});
		</script>
	</body>
</html>