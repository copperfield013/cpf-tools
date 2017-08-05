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
		</style>
	</head>
	<body>
		<div id="page">
			<h2>60×40</h2>
			<div class="container">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-lg-2">From</label>
						<div class="col-lg-8">
							<input type="text" id="from" value="区" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2">To</label>
						<div class="col-lg-8">
							<input type="text" id="to" value="街道" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-3 col-lg-4  form-inline">
							<input id="swap" class="form-control btn btn-primary" type="button" value="交换" />
							<input id="print" class="form-control btn btn-primary" type="button" value="打印" />
						</div>
					</div>
				</form>
			</div>
		</div>
		
		
		
		
		<script type="text/x-jquery-tmpl" id="print-table">
			<div id="print-area">
				<table >
					<tbody>
						<tr>
							<td>
								From:
								<span class="big from">\${from}</span>
							</td>
						</tr>
						<tr>
							<td>
								To:
								<span class="big to">\${to}</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>	
		</script>
		
	
		<script type="text/javascript">
			$(function(){
				$('#print').click(function(){
					var $printPage = $('#print-table').tmpl({
						from	: $('#from').val(),
						to		: $('#to').val()
					});
					
					$printPage.printArea({ 
			    		mode 		: "iframe", 
			    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
			    		extraCss	: $('base').attr('href') + 'media/css/fromTo.css',
			    		posWidth	: '70mm',
			    		posHeight	: '50mm'
			    	})
				});
				
				$('#swap').click(function(){
					var from = $('#from').val(),
						to = $('#to').val();
					
					$('#from').val(to);
					$('#to').val(from);
				});
				
				
			});
		</script>
	</body>
</html>