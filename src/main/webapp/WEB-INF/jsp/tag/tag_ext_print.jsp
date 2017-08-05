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
						<label class="col-lg-2">区域</label>
						<div class="col-lg-8">
							<input type="text" max="50" min="1" class="form-control" id="area" value="下城区" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2">条线</label>
						<div class="col-lg-8 form-inline">
							<select class="form-control inline" id="ptypes">
							</select>
							<select class="form-control inline" id="types">
							</select>
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
							<th>条线</th>
							<th>数量</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
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
				<table>
					<tbody>
						<tr>
							<td rowspan="3" width="50%">
								<img class="qrcode" src="${basePath}\${qrCodeURI}">
								<div class="qrcode">\${qrCode}</div>
							</td>
							<td class="area" width="42%">\${area }-<span class="exttypecode">\${extTypeCode }</span></td>
						</tr>
						<tr>
							<td class="exttype">\${extTypeName }</td>
						</tr>
						<tr>
							<td class="tip">办证审批专用</td>
						</tr>
					</tbody>
				</table>
			</div>
		</script>
		<script type="text/javascript">
			$(function(){
				var EXT_MAP = {
					renshe	: {
						cname	: '人社',
						types	: {
							jycy	: {
								cname	: '就业创业',
								code	: '0A'
							},
							qtfw	: {
								cname	: '企退服务',
								code	: '02'
							},
							shbx	: {
								cname	: '社会保险',
								code	: '03'
							},
							ldjc	: {
								cname	: '劳动监察',
								code	: '04'
							}
						}
					},
					jisheng	: {
						cname	: '计生',
						types	: {
							rkjs	: {
								cname	: '人口计生',
								code	: '05',
							},
							zsysp	: {
								cname	: '再生育审批',
								code	: '01'
							}
						}
					},
					minzheng	: {
						cname	: '民政',
						types	: {
							laonian	: {
								cname	: '老年',
								code	: '06'
							},
							canji	: {
								cname	: '残疾',
								code	: '07'
							},
							bangfu	: {
								cname	: '社会救助',
								code	: '08'
							}
						}
					},
					renwu	: {
						cname	: '人武',
						types	: {
							bingyi	: {
								cname	: '兵役',
								code	: '09'
							}
						}
					}
				}
				
				var EXT_CODE_MAP = {};
				
				var $ptypes = $('#ptypes'),
					$types = $('#types');
				for(var pkey in EXT_MAP){
					$ptypes.append('<option value="' + pkey + '">' + EXT_MAP[pkey].cname + '</option>');
					var types = EXT_MAP[pkey].types;
					for(var key in types){
						EXT_CODE_MAP[types[key].code] = types[key];
						types[key].parent = EXT_MAP[pkey];
					}
				}
				$ptypes.change(function(){
					$types.empty();
					var ptype = EXT_MAP[$ptypes.val()]
					for(var key in ptype.types){
						$types.append('<option value="' + ptype.types[key].code + '">' + ptype.types[key].cname + '</option>');
					}
				}).trigger('change');
				
				var $tbody = $('tbody')
				$('#add').click(function(){
					var count = $('#count').val();
					if(count){
						count = Number(count);
						if(count > 200){
							alert('只允许小于200以内');
							return false;
						}
						var type = EXT_CODE_MAP[$types.val()];
						var $tr = $('<tr code="' + type.code + '" count="' + count + '">');
						$tr.append('<td>' + type.cname + '</td>')
							.append('<td>' + count + '</td>')
							.append('<td><a class="del href="javascript:;">删除</a></td>')
							.appendTo($tbody);
						disablePrint();
					}
				});
				$tbody.on('click', 'a.del', function(){
					$(this).closest('tr').remove();
					disablePrint();
					return false;
				});
				
				var printData;
				function disablePrint(){
					printData = null;
					$('#print').attr('disabled', 'disabled');
				}
				
				
				$('#submit').click(function(){
					var data = [];
					var allCount = 0;
					$tbody.find('tr[code]').each(function(){
						var item = {
							code	: $(this).attr('code'),
							count	: Number($(this).attr('count'))
						};
						data.push(item);
						allCount += item.count;
					});
					if(allCount == 0){
						alert('请添加后再执行');
						return false;
					}
					if(allCount > 200){
						alert('不能一次打印200张以上');
						return false;
					}
					$.ajax({
						//提交的地址
						url		: 'tag/generateTags',
						method	: 'POST',
						dataType: 'json',
						headers	: {
							'content-type'	: 'application/json;charset=utf-8'
						},
						data	: JSON.stringify({data:data})
					}).done(function(_data, textStatus, jqXHR){
						if(typeof data === 'string'){
							printData = $.parseJSON(_data);
						}else{
							printData = _data;
						}
						if(printData.items){
							$('#print').removeAttr('disabled');
						}
					});
				});
				
				$('#print').click(function(){
					if(printData){
						var $content = $('<div>');
						var area = $('#area').val();
						for(var i in printData.items){
							var item = printData.items[i];
							var type = EXT_CODE_MAP[item.extTypeCode];
							var $page = $('#tag-page').tmpl({
								qrCodeURI	: printData.folder + item.code + '.png',
								qrCode		: item.code,
								area		: area,
								extTypeCode	: item.extTypeCode,
								extTypeName	: type.parent.cname + '/' + type.cname
							});
							$content.append($('<div class="wrapper">').append($page));
						}
						$content.printArea({ 
				    		mode 		: "iframe", 
				    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
				    		extraCss	: $('base').attr('href') + 'media/css/print_ext_tag.css',
				    		posWidth	: '60mm',
				    		posHeight	: '40mm'
				    	})
					}
				});
				
			});
			
			
		</script>
	</body>
</html>