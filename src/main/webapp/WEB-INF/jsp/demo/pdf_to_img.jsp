<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<title>PDF转图片</title>
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link href="media/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="media/js/jquery-3.2.1.js"></script>
		<script type="text/javascript" src="media/js/jquery.tmpl.js"></script>
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="media/js/bootstrap.min.js"></script>
		<script src="media/js/jquery.tmpl.js"></script>
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
				<form class="form-horizontal">
					<div class="form-group">
						<label class="col-lg-2 control-label">数据文件</label>
						<div class="col-lg-8">
							<input name="file" id="file" type="file" class="form-control" accept=".pdf">
						</div>
					</div>
					<div  class="form-group">
						<label class="col-lg-2 control-label">转换页码</label>
						<div class="col-lg-9">
							<input id="pageNoStr" name="pageNoStr" type="text">
							<p class="help-block" style="font-size: 10px;">
								页码从1开始。连续页码用“-”表示，单独页码用“,”分隔，不填写时转换全部
							</p>
						</div>
					</div>
					<div class="form-group" >
						<label class="col-lg-2 control-label">分辨率</label>
						<div class="col-lg-8">
							<input name="dpi" type="text" value="500">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">进度</label>
						<div class="col-lg-8">
							<div id="progress" class="progress progress-striped active">
                                <div class="progress-bar progress-bar-orange" 
                                	role="progressbar" 
                                	aria-valuenow="0" 
                                	aria-valuemin="0" 
                                	aria-valuemax="100" 
                                	style="width: 0">
                                    <span>
                                        0
                                    </span>
                                </div>
                            </div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-3 col-lg-6">
							<input id="submit" type="button" class="btn btn-primary btn-lg" value="转换" />
							<input id="reset-btn" type="button" class="btn btn-primary btn-lg" value="重新转换" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-8 col-lg-offset-2" id="feedback-msg" style="word-break:break-all;">
							
						</div>
					</div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				var $feedback = $('#feedback-msg');
				var pageNoList = null;
				var lastUUID = null;
				
				function download(url){
					var $downloadFrame = $('#cpf-download-frame');
					if($downloadFrame.length === 0){
						$downloadFrame = $('<iframe>');
						$downloadFrame.attr('width', 0).attr('height', 0).css('display', 'none');
						$downloadFrame.attr('src', url);
						$downloadFrame.appendTo(document.body);
					}else{
						$downloadFrame[0].contentWindow.location.href = url;
					}
				}
				
				function pageNoResolve(){
					var str = $('#pageNoStr').val();
					var pageNoArr = [];
					if(str != ''){
						var split = str.split(',');
						for(var i in split){
							var pageNo = Number(split[i]);
							if(!isNaN(pageNo) && pageNo > 0){
								pageNoArr.push(pageNo);
							}else{
								var range = split[i].split('-');
								if(range.length == 2){
									var begin = Number(range[0]),
										end = Number(range[1]);
									if(!isNaN(begin) && !isNaN(end) && begin <= end){
										for(var j = begin; j <= end; j++){
											pageNoArr.push(j);
										}
									}
								}
							}
						}
						$.uniqueSort(pageNoArr);
						pageNoArr.sort(function(a, b){return a-b});
						$feedback.text('打印页码：'+ pageNoArr );
						pageNoList = pageNoArr;
					}else{
						pageNoList = null;
					}
				}
				
				$('#pageNoStr').keypress(pageNoResolve).change(pageNoResolve);
				
				$('#reset-btn').click(function(){
					$('form')[0].reset();
					$('#submit').removeClass('redownload').val('提交');
					pageNoResolve();
					pageNoList = null;
					lastUUID = null;
				});
				
				$('#submit').click(function(){
					if($(this).is('.redownload') && lastUUID){
						download('demo/download_pdf_converted/' + lastUUID);
						return false;
					}
					pageNoResolve();
					var msg = '';
					var pageNoValue = '';
					if(pageNoList == null){
						msg = '转换所有页';
					}else if(pageNoList.length > 0){
						for(var i in pageNoList){
							pageNoValue += pageNoList[i] + ',';
						}
						pageNoValue = pageNoValue.substr(0, pageNoValue.length - 1);
						msg = '将转换以下页码的对应的页面（如果存在的话）\r\n[' + pageNoValue + ']';
					}else{
						alert('页码格式错误');
						return false;
					}
					var fileName = $('#file').val();
					if(!fileName){
						alert('请先选择要上传的文件');
						return false;
					}
					
					if(confirm(msg + '\r\n确认转换？')){
						var formData = new FormData($('form')[0]);
						formData.append('pageNo', pageNoValue);
						$feedback.text('开始上传文件');
						$.ajax({
						    url: 		'demo/ajax_convert_pdf.html',
						    type: 		'post',
						    cache: 		false,
						    data: 		formData,
						    processData: false,
						    contentType: false,
						    success		: function(data, status, jqXHR){
						    	if(typeof data === 'string'){
						    		data = $.parseJSON(data);
						    	}
						    	if(data.status === 'start' && data.uuid){
							    	$feedback.text('文件上传完成');
							    	var timer = setInterval(function(){
							    		var uuid = data.uuid;
										$.post('demo/ajax_convert_pdf_status',{uuid : uuid}, function(data){
											console.log(data);
											var progressObj = data.progress;
											if(progressObj){
												if(typeof progressObj.progress === 'number' && typeof progressObj.total === 'number'){
													var progress = progressObj.progress/progressObj.total;
													var percent = parseFloat(progress * 100).toFixed(0);
													$('#progress')
														.find('.progress-bar')
														.attr('aria-valuenow', percent)
														.css('width', percent + '%')
														.find('span')
															.text(percent + '%');
														
													if(data.status === 'ended'){
														clearInterval(timer);
														$feedback.text('转换完成，开始下载');
														lastUUID = uuid;
														$('#submit').addClass('redownload').val('重新下载');
														download('demo/download_pdf_converted/' + uuid);
													}else{
														$feedback.text(progressObj.msg);
													}
												}
											}
											if(data.status !== 'valid'){
												clearInterval(timer);
											}
										});
									}, 700);
						    	}
						    }
						});
					}
				});
			});
		</script>
	<!-- 
		<div id="left">
			<form action="demo/converter_pdf_to_img.html" method="post" enctype="multipart/form-data">
				<label>数据文件</label>
				<input name="file" type="file" >
				分辨率：<input name="dpi" type="text" value="500">
				<input type="submit" value="转换" >
			</form>
			<div>
				转换后的图片都放在了zip压缩包文件里面<br/>
				分辨率一般来说500就蛮高的了，
				分辨率越高，转换时间越长，图片文件越大。<br/>
				如果要转的PDF的页数非常多（十几张以上），那么转换时间可能会很长，
				后续有时间的话我会做个进度条<br/>
			</div>
		</div> -->
	</body>
</html>