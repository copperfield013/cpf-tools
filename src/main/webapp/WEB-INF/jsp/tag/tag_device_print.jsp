<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<title>批量设备标签打印</title>
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
				left: 10%;
				top: 20px;
				width: 60em;
				border: 1px solid;
				padding: 10px 20px;
			}
			td,th{
				text-align: center;
			}
			.container{
				width: 100% !important;
			}
		</style>
	</head>
	<body>
		<div id="page">
			<div class="container">
				<form class="form-horizontal" role="form" action="tag/upload_device_file"  method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-lg-2">文件</label>
						<div class="col-lg-8">
							<label id="fileName">${fileName }</label>
							
							<input type="file" name="file" id="file" style="${fileName==null? '': 'display:none;'}">
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-3 col-lg-6">
							<input id="upload" type="submit" disabled="disabled" class="btn btn-primary btn-lg" value="上传" style="${fileName==null? '': 'display:none;'}" />
							<input id="reupload" type="button" class="btn btn-primary btn-lg" value="重新上传" style="${fileName!=null? '': 'display:none;'}" />
							<input id="download" type="button" class="btn btn-primary btn-lg" value="下载模板" />
						</div>
					</div>
				</form>
				<div>
					<table class="table">
						<thead>
							<tr>
								<th>序号</th>
								<th>资产名称</th>
								<th>型号规格</th>
								<th>资产编号</th>
								<th>启用日期</th>
								<th>使用人员</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="data-body">
							<c:forEach items="${deviceMap }" var="item">
								<tr data="item">
									<td>${item.value['序号'] }</td>
									<td data="name">${item.value['资产名称'] }</td>
									<td data="model">${item.value['型号规格'] }</td>
									<td data="code">${item.value['资产编号'] }</td>
									<td data="date">${item.value['启用日期'] }</td>
									<td data="user">${item.value['使用人员'] }</td>
									<td><a class="del" href="javascript:;">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="6">
									<input id="print" type="button" class="btn btn-primary" value="打印标签" />
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
		<script type="text/x-jquery-tmpl" id="tag-page">
			<div id="container">
				<table>
					<thead>
						<tr>
							<th colspan="2">杭州设维固定资产标签</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="title">资产名称</td>
							<td class="data">
								\${name}
							</td>
						</tr>
						<tr>
							<td class="title">型号规格</td>
							<td class="data">
								\${model}
							</td>
						</tr>
						<tr>
							<td class="title">资产编号</td>
							<td class="data">
								\${code}
							</td>
						</tr>
						<tr>
							<td class="title">启用日期</td>
							<td class="data">
								\${date}
							</td>
						</tr>
						<tr>
							<td class="title">使用人员</td>
							<td class="data">
								\${user}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</script>
		<script type="text/javascript">
			$(function(){
				$('#file').change(function(){
					$('#upload').removeAttr('disabled');
				});
				$('#reupload').click(function(){
					$('#file').show();
					$('#fileName').remove();
					$('#upload').show();
					$(this).hide();
				});
				
				
				$('#download').click(function(){
					location.href = 'tag/download_device_tmpl';
				});
				$('#print').click(function(){
					if(confirm('确认打印？')){
						var $content = $('<div>');
						$('#data-body').find('[data="item"]').each(function(){
							var $this = $(this);
							$('#tag-page').tmpl({
								name	: $this.find('[data="name"]').text(),
								model	: $this.find('[data="model"]').text(),
								code	: $this.find('[data="code"]').text(),
								date	: $this.find('[data="date"]').text(),
								user	: $this.find('[data="user"]').text()
							}).appendTo($('<div class="wrapper">').appendTo($content));
						});
						$content.printArea({ 
				    		mode 		: "iframe", 
				    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
				    		extraCss	: $('base').attr('href') + 'media/css/print_tag.css',
				    		posWidth	: '60mm',
				    		posHeight	: '40mm',
				    		deferMill	: 2000
				    	});
					}
				});
				$('#data-body').on('click', 'a.del', function(){
					$(this).closest('tr').remove();
					return false;
				});
			});
		</script>
	</body>
</html>