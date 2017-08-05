<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<script src="media/plugins/iris/json2.js"></script>
		<script src="media/plugins/iris/jquery-1.9.1.min.js"></script>
		<style type="text/css">
			.contentvideo {
				text-align: center;
				width: 570;
				height: 412x;
				border: solid 4px;
				margin: 0 auto;
				border-color: black;
				background-color: whilesmoke;
				padding: 10px;
			}
		</style>
	</head>
	<body>
		<div id="contentvideoJS" class="contentvideo">
			<object classid="clsid:ED58FE24-056F-45F6-9BE9-3751BDCFB67C" id="irisIdentity" width="548" height="400"></object>
		</div>
		<div>
			<input type="button" value="注册" id="enrollButton" />
			<input type="text" id="uuid" />
			<input type="button" value="识别" id="matchButton" />
			<input type="button" value="停止" id="stopButton" />
		</div>
		<script type="text/javascript">
			$(function(){
				//识别按钮
				$('#matchButton').click(function(){
					//获得uuid
					var uuid = $('#uuid').val();
					if(uuid){
						//发送uuid到服务器获得对应的虹膜数据
						$.get('http://122.224.76.26:8081/eyeCloud/v4/Template/' + uuid, function(data){
							if(data.result){
								//有对应的虹膜数据
								var result = '';
								//组装数据
								for(var i in data.data.TempData[0].leftTempArray){
									result += data.data.TempData[0].leftTempArray[i];
								}
								for(var i in data.data.TempData[0].rightTempArray){
									result += data.data.TempData[0].rightTempArray[i];
								}
								//开启匹配定时器
								var matchInterval = setInterval(function(){
									var matchResult = irisIdentity.GetResult;
									if(matchResult === 0){
										alert('匹配成功');
									}else if(matchResult < 0){
										//匹配超时
										alert('匹配失败');
									}else{
										return;
									}
									clearInterval(matchInterval);
									matchInterval = null;
								}, 50);
								
								//停止匹配
								$('#stopButton').one('click', function(){
									clearInterval(matchInterval);
									matchInterval = null;
									irisIdentity.EndMatch();
								});
								
								//开始比对
								irisIdentity.StartMatch(result, data.data.TempData[0].leftTempArray.length, data.data.TempData[0].rightTempArray.length);
							}else{
								alert(JSON.stringify(data));
								//提示错误信息
								alert(data.errorMessage);
							}
						});
					}
				});
				//注册按钮
				$('#enrollButton').click(function(){
					//设定定时器循环检测控件的状态
					var enrollInterval = setInterval(function(){
						//获得控件的虹膜识别情况
						var temp = irisIdentity.GetEnrollTemplates();
						//如果虹膜已经识别完成
						if(temp){
							//解除定时器
							var tempInterval = enrollInterval;
							enrollInterval = null;
							clearInterval(tempInterval);
							//发送虹膜信息到服务器
							sendIrisTemplate(temp, function(uuid){
								if(uuid){
									$('#uuid').val(uuid);
									alert('注册成功' + uuid);
								}
							});
						}
					}, 50);
					
					//停止匹配
					$('#stopButton').one('click', function(){
						clearInterval(enrollInterval);
						enrollInterval = null;
						irisIdentity.EndEnroll();
					});
					
					//打开摄像头开始注册
					irisIdentity.StartEnroll('', '');
				});
				
				
				/**
				* 
				*/
				function sendIrisTemplate(temp, callback){
					//构造参数用于发起请求
					var tempStr = temp.toString();
					//构造虹膜信息
					var leftTempArray = tempStr.substr(0, 1368),
						rightTempArray = tempStr.substr(1368, 1368);
					//生成uuid
					var uuid = UUID();
					//构造请求对象
					var req = {
				            "uuid": uuid,
				            "leftTempArray": [leftTempArray],
				            "rightTempArray": [rightTempArray],
				            "groupInfoObj": 
							{
				               "province":"浙江",
				               "city":"杭州",
				               "district":"临安" 
				        	}  
				        };
					var reqStr = JSON.stringify(req);
					//开启跨域访问				
					jQuery.support.cors=true;
					//发送异步请求到虹膜服务器，使其保存当前的虹膜信息
					$.post(
							'http://122.224.76.26:8081/eyeCloud/v4/Template',
							reqStr, 
							function(data){
								if(data.result){
									//保存成功
									callback.apply(data, [uuid, data.result]);
								}else{
									//保存失败
									callback.apply(data, [null]);
								}
					});
				}
				
				
				function UUID() {
			        var s = [];
			        var hexDigits = "0123456789abcdef";
			        for (var i = 0; i < 36; i++) {
			            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
			        }
			        s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
			        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
			        s[8] = s[13] = s[18] = s[23] = "-";

			        var uuid = s.join("");
			        return uuid;
			    }
			});
			
			
		</script>
	</body>
</html>