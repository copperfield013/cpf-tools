<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>工具箱-By张荣波</title>
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link href="media/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="media/js/jquery-3.2.1.js"></script>
		<script type="text/javascript" src="media/js/jquery.tmpl.js"></script>
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="media/js/bootstrap.min.js"></script>
		<style type="text/css">
			div.page{
				position: absolute;
				left: 0;
				right: 0;
				top: 2em;
			}
			.panel .row:not(:first-child){
				margin-top: 15px;
				height: 
			}
		</style>
	</head>
	<body>
		<div class="page">
			<div class="row">
				<div class="col-lg-offset-2 col-lg-8">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title">人口导入工具</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<input class="btn btn-info" type="button" value="随机名称生成" onclick="location.href='demo/excel_rename.html'" />
									<input class="btn btn-info" type="button" value="Excel判断户主" onclick="location.href='demo/goCheckHasHouseholder.html'" />
									<input class="btn btn-info" type="button" value="Excel导出楼栋" onclick="location.href='demo/export_building.html'" />
									<input class="btn btn-info" type="button" value="Excel导出重复" onclick="location.href='demo/export_repeat.html'" />
									<input class="btn btn-info" type="button" value="Excel覆盖类型" onclick="location.href='pimport/replace_typeint'" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-offset-2 col-lg-8">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">文件转换工具</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<a class="btn btn-primary" href="demo/pdf_to_img.html" >PDF转图片</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-offset-2 col-lg-8">
					<div class="panel panel-danger">
						<div class="panel-heading">
							<h3 class="panel-title">标签打印</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<input class="btn btn-danger" target="_blank" type="button" value="设备标签" onclick="location.href='demo/print_tag.html'" />
									<input class="btn btn-danger" target="_blank" type="button" value="批量设备标签" onclick="location.href='tag/device'" />
								</div>
							</div>
							<div class="row">
								<div class="col-lg-10">
									<a class="btn btn-danger" target="_blank" href="tag/static_ext_tag">1、条线二维码</a>
									<a class="btn btn-danger" target="_blank" href="tag/event_tag">2、物流</a>
									<a class="btn btn-danger" target="_blank" href="tag/ext_contact">3、条线联系方式</a>
									<a class="btn btn-danger" target="_blank" href="tag/referList">4、材料清单</a>
									<a class="btn btn-danger" target="_blank" href="tag/cert">5、办证事项To</a>
									<a class="btn btn-danger" target="_blank" href="tag/event_tag">6、街道->区</a>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-10">
									<a class="btn btn-danger" target="_blank" href="tag/device">7、公司资产</a>
									<a class="btn btn-danger" target="_blank" href="tag/cert">8、办证事项From</a>
									<a class="btn btn-danger" target="_blank" href="tag/fromTo">9、区->街道</a>
									<a class="btn btn-danger" target="_blank" href="tag/cert">10、个人办证材料</a>
									<a class="btn btn-danger" target="_blank" href="tag/ext_devide">12、条线分支</a>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-10">
									<a class="btn btn-danger" target="_blank" href="tag/portfolio">13、档案袋</a>
									<a class="btn btn-danger" target="_blank" href="tag/comm_prop">14、街道资产</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- <div class="list">
			<dl>
				<dt>人口导入工具</dt>
				<dd>
					<input type="button" value="随机名称生成" onclick="location.href='demo/excel_rename.html'" />
					<input type="button" value="Excel判断户主" onclick="location.href='demo/goCheckHasHouseholder.html'" />
					<input type="button" value="Excel导出楼栋" onclick="location.href='demo/export_building.html'" />
					<input type="button" value="Excel导出重复" onclick="location.href='demo/export_repeat.html'" />
					<input type="button" value="Excel覆盖类型" onclick="location.href='pimport/replace_typeint'" />
				</dd>
			</dl>
			<dl>
				<dt>文件转换工具</dt>
				<dd>
					<input type="button" value="PDF转图片" onclick="location.href='demo/pdf_to_img.html'" />
				</dd>
			</dl>
			<dl>
				<dt>西湖的水</dt>
				<dd>
					<input type="button" value="设备标签" onclick="location.href='demo/print_tag.html'" />
					<input type="button" value="批量设备标签" onclick="location.href='tag/device'" />
					<input type="button" value="条线标签" onclick="location.href='tag/ext'" />
				</dd>
			</dl>
		</div> -->
	</body>
</html>