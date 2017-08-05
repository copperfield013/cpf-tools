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
		<script type="text/javascript" src="media/js/jquery.PrintArea.js"></script>
		<script src="media/plugins/jquery-ui-1.12.1.custom/jquery-ui.js"></script>
		<script src="media/js/jquery.ui.datepicker-zh-CN.js"></script>
		<script src="media/plugins/fileUpload/jquery.fileupload.js"></script>
		
		<style type="text/css">
			*{
				font-size: 20px;
				font-family: 微软雅黑;
			}
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
			#container{
				width: 600px;
				height: 500px;
				border: 1px solid;
			}
			table{
				width: 100%;
				height: 100%;
			}
			table, thead, tbody, tr, td, th{
			    border-collapse: collapse;
			    padding: 0.2em 1em;
			    text-align: center;
			    white-space: nowrap;
			    border: 1px solid;
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
			span.exttypecode {
			    font-size: inherit;
			}
			img.qrcode{
				width: 80%;
			}
			.area{
				font-size: 2em;
			}
			.exttype{
				font-size: 2em;
			}
			.tip{
				font-size: 2em;
			}
			
		</style>
	</head>
	<body>
		<div id="full">
			<div id="container">
				<table>
					<!-- <thead>
						<tr>
							<th colspan="2">
								<select>
									<option>--</option>
									<option>人社</option>
									<option>计生</option>
									<option>民政</option>
								</select>
								<select>
									<option>--</option>
								</select>
							</th>
						</tr>
					</thead> -->
					<tbody>
						<tr>
							<td rowspan="3" width="50%">
								<img class="qrcode" src="${basePath}media/image/qrcode3.png">
								<div class="qrcode">HS08004VeM9sE</div>
							</td>
							<td class="area" width="40%">下城区-<span class="exttypecode">08</span></td>
						</tr>
						<tr>
							<td class="exttype">民政/帮扶</td>
						</tr>
						<tr>
							<td class="tip">办证审批专用</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2">
								<i class="font-resize add">+</i>
								<i class="font-resize sub">-</i>
								<i id="print">打印</i>
								<button id="pbutton">批量打印</button>
							</td>
						</tr>
						<!-- <tr>
							<td colspan="2">
								<input type="file" id="file" name="file" />
								<input type="button" id="upload" value="批量打印" />
							</td>
						</tr> -->
					</tfoot>
				</table>
			</div>
			<textarea id="text"></textarea>
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
				    		extraCss	: $('base').attr('href') + 'media/css/print_ext_tag.css'
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
				
				$('#pbutton').click(function(){
					var text = $('#text').val();
					var a = text.split('\n');
					
					var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>';
					var options = { 
			    		mode 		: "iframe", 
			    		extraHead 	: headElements,
			    		extraCss	: $('base').attr('href') + 'media/css/print_ext_tag.css'
			    	};
					var $container = $('#container').clone();
					$container.find('tfoot').remove();
					var $wrapper = $('<div>');
					for(var i in a){
						console.log(a[i]);
						var $c = $container.clone();
						$c.find('img.qrcode').attr('src', 'http://192.168.2.13:7080/tools/media/image/qrcode1/' + a[i] + '.png');
						$c.find('div.qrcode').text(a[i]);
						var exttypecode = a[i].substr(2, 2);
						var exttype = getTypeName(exttypecode);
						$c.find('.exttypecode').text(exttypecode);
						$c.find('.exttype').text(exttype);
						$wrapper.append($('<div class="wrapper">').append($c));
					}
					$wrapper.printArea(options);
				});
				
				function getTypeName(code){
					switch(code){
						case '01': return '人社/就业创业';
						case '02': return '人社/企退服务';
						case '03': return '人社/社会保险';
						case '04': return '人社/劳动监察';
						case '05': return '计生/人口计生';
						case '06': return '民政/老年';
						case '07': return '民政/残疾';
						case '08': return '民政/帮扶';
						case '09': return '人武/兵役';
					}
				}
				
			});
		</script>
	</body>
</html>