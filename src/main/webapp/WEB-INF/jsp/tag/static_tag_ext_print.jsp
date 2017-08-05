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
						<div class="col-lg-offset-3 col-lg-6 form-inline">
							<input id="add" class="form-control inline" type="button" value="添加" />
							<input id="add-all" class="form-control inline" type="button" value="添加全部" />
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
						jisheng	: {
							cname	: '计生',
							types	: {
								'type_01': {cname:'再生育审批',code:'01'},
								'type_02': {cname:'并发症鉴定',code:'02'},
								'type_03': {cname:'扶助金申请',code:'03'},
								'type_04': {cname:'扶助金核发',code:'04'},
								'type_05': {cname:'公益金申请',code:'05'},
								'type_06': {cname:'公益金核发',code:'06'},
								'type_07': {cname:'病残儿鉴定',code:'07'},
								'type_08': {cname:'生育证明',code:'08'},
								'type_09': {cname:'独生光荣证',code:'09'},
								'type_0A': {cname:'生育登记',code:'0A'},
								'type_0B': {cname:'奖励金核发',code:'0B'},
								'type_0C': {cname:'引流产证明',code:'0C'},
								'type_0D': {cname:'流动证明',code:'0D'},
								'type_0E': {cname:'安康保险',code:'0E'},
								'type_0F': {cname:'养老照护',code:'0F'},
								'type_0G': {cname:'国免孕检',code:'0G'},
								'type_0H': {cname:'避孕药具',code:'0H'},
								'type_0I': {cname:'取放环证明',code:'0I'},
							}
						},
						yanglao	: {
							cname	: '养老',
							types	: {
								'type_0J': {cname:'老年优待证',code:'0J'},
								'type_0K': {cname:'养老套餐',code:'0K'},
								'type_0L': {cname:'手机申请',code:'0L'},
								'type_0M': {cname:'生活津贴',code:'0M'},
								'type_0N': {cname:'家电统保',code:'0N'}
							}
						},
						jiuzhu	: {
							cname	: '救助',
							types	: {
								'type_0O': {cname:'残疾呼叫机',code:'0O'},
								'type_0P': {cname:'住院补贴',code:'0P'},
								'type_0Q': {cname:'救助认定',code:'0Q'},
								'type_0R': {cname:'特困供养',code:'0R'},
								'type_0S': {cname:'侨眷低保',code:'0S'},
								'type_0T': {cname:'困难家庭',code:'0T'},
								'type_0U': {cname:'低收入家庭',code:'0U'},
								'type_0V': {cname:'低保家庭',code:'0V'},
								'type_0W': {cname:'低保对象',code:'0W'},
								'type_0X': {cname:'残疾对象',code:'0X'},
								'type_0Y': {cname:'区困救助',code:'0Y'},
								'type_0Z': {cname:'就学救助',code:'0Z'},
								'type_0a': {cname:'白内障补助',code:'0a'},
								'type_0b': {cname:'残疾认定',code:'0b'},
								'type_0c': {cname:'残疾护补',code:'0c'},
								'type_0d': {cname:'残疾房补',code:'0d'},
								'type_0e': {cname:'残疾电补',code:'0e'},
								'type_0f': {cname:'残疾油补',code:'0f'},
								'type_0g': {cname:'残疾生理补',code:'0g'},
								'type_0h': {cname:'残疾医保补',code:'0h'},
								'type_0i': {cname:'无障碍设施',code:'0i'},
								'type_0j': {cname:'残疾人创业',code:'0j'},
								'type_0k': {cname:'残疾养老险',code:'0k'},
								'type_0l': {cname:'低视力补助',code:'0l'},
								'type_0m': {cname:'精神病补助',code:'0m'},
								'type_0n': {cname:'助听器补助',code:'0n'},
								'type_0o': {cname:'残疾人就学',code:'0o'},
								'type_0p': {cname:'康复补助',code:'0p'},
								'type_0q': {cname:'康复器材',code:'0q'},
								'type_0r': {cname:'护理补贴',code:'0r'},
								'type_0s': {cname:'慰问补助',code:'0s'},
								'type_0t': {cname:'残疾儿童',code:'0t'},
								'type_0u': {cname:'生活补贴',code:'0u'},
								'type_0v': {cname:'意外伤害',code:'0v'},
								'type_0w': {cname:'无固定收入',code:'0w'},
								'type_0x': {cname:'医疗补贴',code:'0x'},
								'type_0y': {cname:'贴息贷款',code:'0y'},
								'type_0z': {cname:'红十字救助',code:'0z'},
								'type_10': {cname:'归正服药',code:'10'},
								'type_11': {cname:'困难家庭',code:'11'},
								'type_12': {cname:'家庭就医',code:'12'},
								'type_13': {cname:'归正救助',code:'13'},
								'type_14': {cname:'困难家庭审',code:'14'},
								'type_15': {cname:'捐助凭证',code:'15'}
							}
						},
						shuangyong	: {
							cname	: '双拥',
							types	: {
								'type_16': {cname:'家属补助',code:'16'},
								'type_17': {cname:'退伍补助',code:'17'},
								'type_18': {cname:'三属优待证',code:'18'},
								'type_19': {cname:'家属慰问金',code:'19'},
								'type_1A': {cname:'复员慰问金',code:'1A'}
							}
						},
						renshe	: {
							cname	: '人社',
							types	: {
								'type_1B': {cname:'就业援助',code:'1B'},
								'type_1C': {cname:'失业登记',code:'1C'},
								'type_1D': {cname:'灵活就业',code:'1D'},
								'type_1E': {cname:'小额贷款',code:'1E'},
								'type_1F': {cname:'农村补助',code:'1F'},
								'type_1G': {cname:'用工补助',code:'1G'},
								'type_1H': {cname:'创业补贴',code:'1H'},
								'type_1I': {cname:'区创业补贴',code:'1I'},
								'type_1J': {cname:'退休续险补',code:'1J'},
								'type_1K': {cname:'失业创业补',code:'1K'},
								'type_1L': {cname:'区用工补贴',code:'1L'},
								'type_1M': {cname:'灵活就业',code:'1M'},
								'type_1N': {cname:'就业助推',code:'1N'},
								'type_1O': {cname:'养老查询',code:'1O'},
								'type_1P': {cname:'退休补助',code:'1P'},
								'type_1Q': {cname:'退休证办理',code:'1Q'},
								'type_1R': {cname:'退休缴费',code:'1R'},
								'type_1S': {cname:'养老卡更换',code:'1S'},
								'type_1T': {cname:'养老号更换',code:'1T'},
								'type_1U': {cname:'养老初审',code:'1U'},
								'type_1V': {cname:'收退休档案',code:'1V'},
								'type_1W': {cname:'查退休档案',code:'1W'},
								'type_1X': {cname:'证退休工龄',code:'1X'},
								'type_1Y': {cname:'查企退档案',code:'1Y'},
								'type_1Z': {cname:'企退技术',code:'1Z'},
								'type_1a': {cname:'军转干部补',code:'1a'},
								'type_1b': {cname:'改退休金号',code:'1b'},
								'type_1c': {cname:'养老金明细',code:'1c'},
								'type_1d': {cname:'生育补助',code:'1d'},
								'type_1e': {cname:'丧葬抚恤金',code:'1e'},
								'type_1f': {cname:'人员证明',code:'1f'},
								'type_1g': {cname:'创业社保补',code:'1g'},
								'type_1h': {cname:'创带补贴',code:'1h'},
								'type_1i': {cname:'毕业就业补',code:'1i'},
								'type_1j': {cname:'小企招毕补',code:'1j'},
								'type_1k': {cname:'毕业生活补',code:'1k'},
								'type_1l': {cname:'务工创业补',code:'1l'},
								'type_1m': {cname:'军队家属补',code:'1m'},
								'type_1n': {cname:'创业房租补',code:'1n'},
								'type_1o': {cname:'军转干部补',code:'1o'},
								'type_1p': {cname:'军转干部预',code:'1p'},
								'type_1q': {cname:'医疗保险',code:'1q'},
								'type_1r': {cname:'养老保险',code:'1r'},
								'type_1s': {cname:'个体参保',code:'1s'},
								'type_1t': {cname:'体检单开具',code:'1t'},
								'type_1u': {cname:'三证维护',code:'1u'},
								'type_1v': {cname:'网办业务',code:'1v'},
								'type_1w': {cname:'病历本领换',code:'1w'},
								'type_1x': {cname:'联系修改',code:'1x'},
								'type_1y': {cname:'养老待遇',code:'1y'},
								'type_1z': {cname:'退休回杭',code:'1z'}
							}
						},
						chengguan	: {
							cname	: '城管',
							types	: {
								'type_20': {cname:'犬证办理',code:'20'}
							}
						},
						zhujian	: {
							cname	: '住建',
							types	: {
								'type_21': {cname:'廉租房承租',code:'21'}
							}
						},
						shuishou	: {
							cname	: '税收',
							types	: {
								'type_22': {cname:'房屋税收',code:'22'}
							}
						},
						sifa		: {
							cname	: '司法',
							types	: {
								'type_23': {cname:'法律援助',code:'23'}
							}
						},
						fuwu		: {
							cname	: '服务',
							types	: {
								'type_24': {cname:'监控查看',code:'24'}
							}
						},
						dangjian	: {
							cname	: '党建',
							types	: {
								'type_25': {cname:'党组织转接',code:'25'}
							}
						}
					};
				
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
				$('#add-all').click(function(){
					var count = $('#count').val();
					if(count){
						count = Number(count);
						if(count > 200){
							alert('只允许小于200以内');
							return false;
						}
						for(var i in EXT_CODE_MAP){
							var type = EXT_CODE_MAP[i];
							var $tr = $('<tr code="' + type.code + '" count="' + count + '">');
							$tr.append('<td>' + type.cname + '</td>')
								.append('<td>' + count + '</td>')
								.append('<td><a class="del href="javascript:;">删除</a></td>')
								.appendTo($tbody);
						}
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
						var pageArray = new Array(1000);
						for(var i in printData.items){
							var item = printData.items[i];
							var type = EXT_CODE_MAP[item.extTypeCode];
							var typeNo = parseDigit(item.extTypeCode);
							var $page = $('#tag-page').tmpl({
								qrCodeURI	: printData.folder + item.code + '.png',
								qrCode		: item.code,
								area		: area,
								extTypeCode	: typeNo,
								extTypeName	: type.parent.cname + '/' + type.cname
							});
							pageArray[typeNo] = $('<div class="wrapper">').append($page);
						}
						for(var i in pageArray){
							if(pageArray[i]){
								$content.append(pageArray[i]);
							}
						}
						$content.printArea({ 
				    		mode 		: "iframe", 
				    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
				    		extraCss	: $('base').attr('href') + 'media/css/print_ext_tag.css',
				    		posWidth	: '60mm',
				    		posHeight	: '40mm',
				    		deferMill	: 5000
				    	})
					}
				});
				
				var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
				function parseDigit(code){
					var cs = code.split('');
					var  sum = 0;
					for (var i = 0; i < cs.length; i++) {
						sum += CHARS.indexOf(cs[i]) * Math.pow(62, cs.length - i - 1);
					}
					return sum;
				}
			});
			
			
		</script>
	</body>
</html>